package me.jiaxu.serviceflow;

import me.jiaxu.serviceflow.annotation.In;
import me.jiaxu.serviceflow.annotation.Out;
import me.jiaxu.serviceflow.annotation.Publish;
import me.jiaxu.serviceflow.annotation.Subscribe;
import me.jiaxu.serviceflow.common.ExceptionEnum;
import me.jiaxu.serviceflow.common.ServiceFlowException;
import me.jiaxu.serviceflow.model.DecorateField;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     工作流启动器，该启动器与spring生命周期结合
 */
public class SpringServiceFlowStarter<T, R>
        implements ServiceFlowStarter<T, R>, InitializingBean, ApplicationContextAware {

    /** 日志名称 */
    private static String loggerName = "SERVICE";

    /** 日志 */
    private static final Logger LOGGER = Logger.getLogger(loggerName);

    /** 模型约束的规则 */
    private String              domainConstraintRule;

    /** 服务单元名称列表，此处定义服务单元执行顺序 */
    private List<String>        serviceUnits;

    /** 异常处理器 */
    private ExceptionHandler    exceptionHandler;

    /** 请求入参 */
    private T request;

    /** 请求出参 */
    private R response;

    /** spring 上下文 */
    private ApplicationContext          applicationContext;

    /** public map */
    private Map<String, DecorateField>  publishMap;

    /** service unit map */
    private Map<String, ServiceUnit>    serviceUnitMap;

    private Long timeSpent;

    /**
     * 初始化
     * 包含对 服务单元实体的获取、图的构建，反射比较耗时，所以在bean初始化时进行处理
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(serviceUnits)) {
            return ;
        }

        // 获取 bean 并校验
        publishMap = new HashMap<>();
        for (String beanName : serviceUnits) {
            if (StringUtils.isBlank(beanName)) {
                continue;
            }
            // 获取 serviceUnits 实体
            ServiceUnit serviceUnit = (ServiceUnit) applicationContext.getBean(beanName);
            if (serviceUnit == null) {
                throw new ServiceFlowException(ExceptionEnum.MISSED_SERVICE_UNIT);
            }
            serviceUnitMap.put(beanName, serviceUnit);
        }

        // 处理图
        for (String beanName : serviceUnits) {
            processFields(serviceUnitMap.get(beanName));
        }
    }

    /** 执行方法 */
    @Override
    public R apply(T request) {
        try {
            // 设置入参
            this.request = request;

            Date startTime = new Date();

            // 执行
            for (String unit : serviceUnits) {
                ServiceUnit serviceUnit = serviceUnitMap.get(unit);

                serviceUnit.before();
                serviceUnit.process();
                serviceUnit.after();
            }

            Date endTime = new Date();
        } catch (Exception ex) {
            // 非引擎异常，执行用户自定义的异常处理逻辑
            if (exceptionHandler != null) {
                exceptionHandler.process(ex);
            }
        }
        return response;
    }

    /**
     * 处理单元中的成员变量
     */
    private void processFields(ServiceUnit unit) throws ServiceFlowException {
        Field[] declaredFields = unit.getClass().getDeclaredFields();
        for (Field field: declaredFields) {
            Annotation[] annotations = field.getAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof In) {
                    processIn(unit, field);
                    break;
                }
                if (annotation instanceof Out) {
                    processOut(unit, field);
                    break;
                }
                if (annotation instanceof Publish) {
                    processPublish(unit, field);
                    break;
                }
                if (annotation instanceof Subscribe) {
                    processSubscribe(unit, field);
                    break;
                }
            }
        }
    }

    /**
     * 处理 @In
     */
    private void processIn(ServiceUnit unit, Field field) throws ServiceFlowException {
        field.setAccessible(true);
        try {
            field.set(unit, request);
        } catch (IllegalAccessException ex) {
            throw new ServiceFlowException();
        }
    }

    /**
     * 处理 @Out
     */
    private void processOut(ServiceUnit unit, Field field) throws ServiceFlowException {
        field.setAccessible(true);
        try {
            // 一个微服务流中只能存在一个出口元素
            if (response != null) {
                throw new ServiceFlowException(ExceptionEnum.DUPLICATE_OUT);
            }
            response = (R) field.get(unit);
        } catch (IllegalAccessException e) {
            throw new ServiceFlowException();
        }
    }

    /**
     * 处理 @Publish
     */
    private void processPublish(ServiceUnit unit, Field field) throws ServiceFlowException {
        field.setAccessible(true);
        try {
            String name = field.getName();

            DecorateField decorateField = new DecorateField(name, field.get(unit));
            publishMap.put(name, decorateField);
        } catch (IllegalAccessException e) {
            throw new ServiceFlowException();
        }
    }

    /**
     * 处理 @Subscribe
     */
    private void processSubscribe(ServiceUnit unit, Field field) throws ServiceFlowException {
        field.setAccessible(true);
        String name = field.getName();

        try {
            // 被订阅的 Field 必须已经被发布
            DecorateField decorateField = publishMap.get(name);
            if (decorateField == null) {
                throw new ServiceFlowException(ExceptionEnum.SUBSCRIBE_BEFORE_PUBLISH);
            }

            field.set(unit, decorateField.getValue());
        } catch (IllegalAccessException e) {
            throw new ServiceFlowException();
        } catch (ServiceFlowException ex) {
            throw ex;
        }
    }


    public static void setLoggerName(String loggerName) {
        SpringServiceFlowStarter.loggerName = loggerName;
    }

    public void setDomainConstraintRule(String domainConstraintRule) {
        this.domainConstraintRule = domainConstraintRule;
    }

    public void setServiceUnits(List<String> serviceUnits) {
        this.serviceUnits = serviceUnits;
    }

    /** springContext 的注入方法 */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
