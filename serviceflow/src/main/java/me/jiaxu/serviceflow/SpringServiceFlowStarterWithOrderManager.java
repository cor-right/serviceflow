package me.jiaxu.serviceflow;

import me.jiaxu.serviceflow.annotation.In;
import me.jiaxu.serviceflow.annotation.Out;
import me.jiaxu.serviceflow.annotation.Publish;
import me.jiaxu.serviceflow.annotation.Subscribe;
import me.jiaxu.serviceflow.common.enums.CommonExceptionEnum;
import me.jiaxu.serviceflow.common.util.CollectionUtils;
import me.jiaxu.serviceflow.model.exception.ServiceFlowEngineCommonException;
import me.jiaxu.serviceflow.model.exception.ServiceFlowEngineRuntimeException;
import me.jiaxu.serviceflow.model.exception.ServiceFlowEngineStartException;
import me.jiaxu.serviceflow.common.enums.ExceptionEnum;
import me.jiaxu.serviceflow.common.constant.LoggerConstants;
import me.jiaxu.serviceflow.common.util.LoggerUtils;
import me.jiaxu.serviceflow.model.DecorateField;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     工作流启动器，使用时需要先指定 FlowOrderManager 的实现类
 *     为了防止spring的单例模式产生不可控的影响，该工作流内部全都使用手动的原型模式
 */
@Component
public class SpringServiceFlowStarterWithOrderManager<T, R> implements ServiceFlowStarter<T, R>, InitializingBean {

    /** 模型约束规则 */
    private String              domainConstraintRule;

    /** 异常处理器 */
    private ExceptionHandler    exceptionHandler;

    /** application context */
    @Autowired
    private SpringContext springContext;

    /** tools */
    @Autowired
    private ServiceFlowTools serviceFlowTools;

    /** request */
    private T request;

    /** response */
    private R response;

    /** 工作流流程定义 */
    private Map<String, FlowOrderManager> allFlowOrderManagerMap = new HashMap<>();

    private Map<String, DecorateField> publishMap = new HashMap<>();

    @Override
    public void setDomainConstraintRule(String rule) {

    }

    @Override
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {

    }

    /**
     * 启动方法
     *
     * @param request
     * @param manager 工作流流程定义实现类
     * @return response
     * @throws Exception
     */
    @Override
    public R apply(T request, FlowOrderManager manager) throws Exception {
        long startTime = new Date().getTime();
        this.request = request;

        try {
            // 获取工作流程和工作单元
            if (manager == null) {
                throw new ServiceFlowEngineRuntimeException(ExceptionEnum.MISSED_FLOW_ORDER_MANAGER);
            }

            // 配置工作流程
            List<ServiceUnit> serviceUnits = new ArrayList<>();
            for (String s : manager.serviceUnitsOrderList()) {
                ServiceUnit serviceUnit = springContext.getServiceUnit(s);
                serviceUnits.add(serviceUnit);
            }
            Map<String, ServiceUnit> serviceUnitsMap = serviceUnits
                    .stream()
                    .collect(Collectors.toMap(unit -> unit.getClass().getName(), Function.identity()));

            for (ServiceUnit unit : serviceUnits) {
                processFields(unit);
            }

            // 执行工作单元的业务逻辑
            for (ServiceUnit unit : serviceUnits) {
                unit.before();
                unit.process();
                unit.after();
            }


        } catch (ServiceFlowEngineRuntimeException  sre) {
            LoggerUtils.error(LoggerConstants.ENGINE_RUN_LOGGER, sre);

        } catch(ServiceFlowEngineCommonException sce) {
            LoggerUtils.error(LoggerConstants.ENGINE_RUN_LOGGER, sce);

        }catch (Exception e) {
            // 用户定义的 workunit 的异常，优先用 exceptioHandler 处理，否则直接 throw
            if (exceptionHandler != null) {
                exceptionHandler.process(e);
            } else {
                throw e;
            }

        } finally {
            long spentTime = new Date().getTime() - startTime;
            LoggerUtils.info(LoggerConstants.ENGINE_RUN_LOGGER, "业务执行时间: " + spentTime);
        }

        return response;
    }


    /**
     * 初始校验
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER, "引擎初始化开始...");

        try {
            // 获取所有工作流流程定义
            Map<String, FlowOrderManager> beansForType = springContext.getBeansForType(FlowOrderManager.class);
            if (CollectionUtils.isEmpty(beansForType)) {
                throw new ServiceFlowEngineStartException(ExceptionEnum.NO_FLOW_ORDER_MANAGER_DEFINED);
            }

            LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER,
                    "获取工作流流程定义对象成功，流程定义对象共有 %d 个.", beansForType.keySet().size());
            LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER, "{");
            beansForType.values().forEach(bean
                    -> LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER, "\t" + bean.getClass().getName()));
            LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER, "}");

            // 校验每个工作流流程
            for (FlowOrderManager flow : beansForType.values()) {
                LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER, "");
                LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER,
                        "---- 开始校验流程定义对象. %s", flow.getClass().getName());
                LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER, "{");

                // 获取 bean
                List<ServiceUnit> unitList = new ArrayList<>();
                for (String s : flow.serviceUnitsOrderList()) {
                    ServiceUnit serviceUnit = null;
                    try {
                         serviceUnit = springContext.getServiceUnit(s);

                         LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER,
                                 "\tbean对象已存在无需注册: %s" , serviceUnit.getClass().getName());
                    } catch (ServiceFlowEngineCommonException commonException) {
                        // 如果是上下文中不存在bean，则进行bean动态注册
                        if (commonException.getErrorEnum().equals(CommonExceptionEnum.BEAN_NOT_EXIST)) {
                            serviceUnit = (ServiceUnit) springContext.registryBean(s);
                        }
                    }
                    unitList.add(serviceUnit);
                }

                // 校验发布和订阅的关系 及 out的唯一性和必要性
                Set<Object> publishedFiled = new HashSet<>();
                boolean containsOut = false;

                for (ServiceUnit unit : unitList) {
                    List<String> inList  = new ArrayList<>();
                    List<String> outList = new ArrayList<>();
                    List<String> subscribeList = new ArrayList<>();
                    List<String> publishList   = new ArrayList<>();

                    Field[] fields = unit.getClass().getDeclaredFields();

                    if (fields.length == 0) {
                        LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER,
                                "\t流程 %s 中服务单元 %50s 校验通过，该单元中不存在成员变量",
                                flow.getClass().getName(), unit.getClass().getName());
                        continue;
                    }

                    for (Field field : fields) {
                        field.setAccessible(true);
                        Annotation[] annotations = field.getDeclaredAnnotations();

                        // 校验：同一 workunit 的 filed 中只允许有一个serviceflow 提供的注解( @In / @Out / @Subscribe / @Publish )
                        List<Annotation> annotationList = Arrays.stream(annotations).filter(annotation
                                -> annotation instanceof Subscribe
                                || annotation instanceof Publish
                                || annotation instanceof In
                                || annotation instanceof Out).collect(Collectors.toList());
                        if (annotationList.size() > 1) {
                            throw new ServiceFlowEngineStartException(ExceptionEnum.TOO_MUCH_DECLARED_ANNOTATIONS);
                        }
                        if (annotationList.size() == 0) {
                            LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER,
                                    "\t流程 %s 中服务单元 %50s 校验通过，该单元中不存在需要检查的成员变量",
                                    flow.getClass().getName(), unit.getClass().getName());
                            continue;
                        }
                        Annotation annotation = annotationList.get(0);

                        if (annotation instanceof In) {
                            inList.add(field.getName());
                        }

                        // 校验：被 @Subscribe 的 field 必须已经 @Publish
                        if (annotation instanceof Subscribe) {
                            subscribeList.add(field.getName());

                            if (!CollectionUtils.contains(publishedFiled, field.get(unit))) {
                                throw new ServiceFlowEngineStartException(ExceptionEnum.SUBSCRIBE_BEFORE_PUBLISH);
                            }
                        }

                        if (annotation instanceof Publish) {
                            publishList.add(field.getName());

                            publishedFiled.add(field.get(unit));
                        }

                        // 校验：@Out 在同一 flow 中最多一个
                        if (annotation instanceof Out) {
                            if (containsOut) {
                                throw new ServiceFlowEngineStartException(ExceptionEnum.DUPLICATE_OUT);
                            } else {
                                outList.add(field.getName());

                                containsOut = true;
                            }
                        }

                        LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER,
                                "\t流程 %s 中服务单元 %50s 校验通过.", flow.getClass().getName(), unit.getClass().getName());
                    }

                    serviceFlowTools.getInMap().put(unit, inList);
                    serviceFlowTools.getOutMap().put(unit, outList);
                    serviceFlowTools.getPublishMap().put(unit, publishList);
                    serviceFlowTools.getSubscribeMap().put(unit, subscribeList);
                }

                // 校验：必须有 @Out
                if (!containsOut) {
                    throw new ServiceFlowEngineStartException(ExceptionEnum.NONE_OUT_FOUND);
                }

                // 校验通过，加入属性中
                allFlowOrderManagerMap.put(flow.getClass().getName(), flow);

                LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER, "}");
                LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER,
                        "流程定义对象校验通过: %s", flow.getClass().getName());

                // 关联关系存到 tools 中一份，方便后续使用

                serviceFlowTools.getServiceUnitMap().put(flow.getClass().getName(), unitList);
                serviceFlowTools.getFlowManagerList().add(flow);
            }
        } catch (ServiceFlowEngineStartException ese) {
            LoggerUtils.error(LoggerConstants.ENGINE_START_LOGGER, ese);

        } catch(Exception e) {
            e.printStackTrace();
            LoggerUtils.error(LoggerConstants.ENGINE_START_LOGGER,
                    new ServiceFlowEngineStartException(ExceptionEnum.DEFAULT_ERROR));
        }

        LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER, "引擎初始化校验成功.");
    }

    /**
     * 处理工作单元中的成员变量
     */
    private void processFields(ServiceUnit unit) throws ServiceFlowEngineRuntimeException {
        Field[] declaredFields = unit.getClass().getDeclaredFields();
        for (Field field: declaredFields) {
            Annotation[] annotations = field.getAnnotations();

            for (Annotation annotation : annotations) {
                try {
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
                } catch (IllegalAccessException iae) {
                    throw new ServiceFlowEngineRuntimeException();

                } catch (ServiceFlowEngineRuntimeException sfe) {
                    throw sfe;
                }

            }
        }
    }

    /**
     * 处理 @In
     */
    private void processIn(ServiceUnit unit, Field field) throws IllegalAccessException {
        field.setAccessible(true);

        field.set(unit, request);
    }

    /**
     * 处理 @Out
     */
    private void processOut(ServiceUnit unit, Field field) throws ServiceFlowEngineRuntimeException, IllegalAccessException {
        field.setAccessible(true);

        // 一个微服务流中只能存在一个出口元素
        if (response != null) {
            throw new ServiceFlowEngineRuntimeException(ExceptionEnum.DUPLICATE_OUT);
        }
        response = (R) field.get(unit);
    }

    /**
     * 处理 @Publish
     */
    private void processPublish(ServiceUnit unit, Field field) throws IllegalAccessException {
        field.setAccessible(true);

        String name = field.getName();
        DecorateField decorateField = new DecorateField(name, field.get(unit));
        publishMap.put(name, decorateField);
    }

    /**
     * 处理 @Subscribe
     */
    private void processSubscribe(ServiceUnit unit, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        String name = field.getName();

        // 被订阅的 Field 必须已经被发布
        DecorateField decorateField = publishMap.get(name);
        field.set(unit, decorateField.getValue());
    }
}
