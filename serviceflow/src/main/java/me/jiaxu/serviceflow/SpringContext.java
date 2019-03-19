package me.jiaxu.serviceflow;

import me.jiaxu.serviceflow.annotation.In;
import me.jiaxu.serviceflow.annotation.Subscribe;
import me.jiaxu.serviceflow.common.enums.CommonExceptionEnum;
import me.jiaxu.serviceflow.common.enums.ExceptionEnum;
import me.jiaxu.serviceflow.common.constant.LoggerConstants;
import me.jiaxu.serviceflow.common.util.LoggerUtils;
import me.jiaxu.serviceflow.common.util.ReflectUtils;
import me.jiaxu.serviceflow.model.DecorateField;
import me.jiaxu.serviceflow.model.exception.ServiceFlowEngineCommonException;
import me.jiaxu.serviceflow.model.exception.ServiceFlowEngineStartException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     spring 上下文工具类
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    /**
     * 获取指定的 bean
     *
     * 在原生方法上添加了自动转换的功能
     *
     * @param name 实现类名
     * @return bean
     */
    public Object getBean(String name) throws ServiceFlowEngineCommonException {

        try {
            return applicationContext.getBean(name);
        } catch (NoSuchBeanDefinitionException nbe) {
            throw new ServiceFlowEngineCommonException(CommonExceptionEnum.BEAN_NOT_EXIST);
        }
    }

    /**
     * 获取工作单元
     *
     * @param name
     * @return
     */
    public ServiceUnit getServiceUnit(String name) throws ServiceFlowEngineCommonException{
        try {
            return (ServiceUnit) getBean(name);
        } catch (ClassCastException cce) {
            throw new ServiceFlowEngineCommonException(CommonExceptionEnum.BEAN_CAST_EXCEPTION);
        }
    }

    /**
     * 获取指定类型的所有 bean
     *
     * 透传
     *
     * @param type bean 接口
     * @param <T>  接口类型
     * @return
     */
    public <T> Map<String, T> getBeansForType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 进行 bean 的动态注册
     *
     * @param name 被注册的类的全限定名
     */
//    public void registryBean(String name, Map<String, DecorateField> publishMap, Object request) throws ServiceFlowEngineStartException {
    public Object registryBean(String name) throws ServiceFlowEngineCommonException {
        // 获得 factory
        ConfigurableApplicationContext configContext = (ConfigurableApplicationContext)applicationContext;
        DefaultListableBeanFactory listableFactory = (DefaultListableBeanFactory) configContext.getBeanFactory();

        // 加载 bean
        Class<?> type = null;
        try {
            type = Class.forName(name);
        } catch (ClassNotFoundException cnfe) {
            throw new ServiceFlowEngineCommonException(CommonExceptionEnum.CLASS_NOT_EXIST);
        }

        // 这里构建 bean 的注册对象
        BeanDefinitionBuilder builder = Optional.ofNullable(type)
                .map(BeanDefinitionBuilder::genericBeanDefinition)
                .orElseThrow(() -> new ServiceFlowEngineCommonException(CommonExceptionEnum.CLASS_NOT_EXIST));

        // 获取类中需要处理的成员变量， 需要设置的成员变量的属性包括 @In @Subscribe 和 @Autowried
//        Arrays
//                .stream(type.getFields())
//                .forEach(field -> {
//                    String fieldName = field.getName();
//
//                    // In 代表该属性的值应该是 request
//                    if (ReflectUtils.containsAnnotation(field, In.class)) {
//                        builder.addPropertyValue(fieldName, request);
//                    }
//                    // subscribe 的值应该去引擎内部图中寻找
//                    else if (ReflectUtils.containsAnnotation(field, Subscribe.class)) {
//                        Object bean = publishMap.get(fieldName).getValue();
//                        builder.addPropertyValue(fieldName, bean);
//                    }
//                    // 自动注入的应该去 spring 容器中寻找
//                    else if (ReflectUtils.containsAnnotation(field, Autowired.class)) {
//                        builder.addPropertyReference(fieldName, fieldName);
//                    }
//                });

        //  必须设置 bean 为原型模式
        builder.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);

        // 注册 bean，name 使用bean类的全限定名来做
        listableFactory.registerBeanDefinition(type.getName(), builder.getBeanDefinition());

        LoggerUtils.debug(LoggerConstants.ENGINE_START_LOGGER, "\tBean 动态注册完成: %s", type.getName());

        return getBean(name);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
