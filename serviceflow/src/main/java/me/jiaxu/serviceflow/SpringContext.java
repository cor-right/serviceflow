package me.jiaxu.serviceflow;

import me.jiaxu.serviceflow.common.enums.CommonExceptionEnum;
import me.jiaxu.serviceflow.common.constant.LoggerConstants;
import me.jiaxu.serviceflow.common.util.LoggerUtils;
import me.jiaxu.serviceflow.model.exception.ServiceFlowEngineCommonException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     spring 上下文工具类
 */
@Component
public class SpringContext implements ApplicationContextAware {

    /** spring context */
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
