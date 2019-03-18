package me.jiaxu.serviceflow;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

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
     * @param type 接口类型
     * @return bean
     */
    public <T> T getBean(String name, Class<T> type) {
        try {
            return type.cast(
                    applicationContext.getBean(name));
        } catch (ClassCastException cce) {
            return null;
        }
    }

    /**
     * 获取工作单元
     *
     * @param name
     * @return
     */
    public ServiceUnit getServiceUnit(String name) {
        return getBean(name, ServiceUnit.class);
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

    public void registryBean(String name) {
        // 获得 factory
        ConfigurableApplicationContext configContext = (ConfigurableApplicationContext)applicationContext;
        DefaultListableBeanFactory listableFactory = (DefaultListableBeanFactory) configContext.getBeanFactory();

        // 构建 bean, TODO: 实现自动注册
        Class<?> type = Class.forName(name);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(name);
        builder.addPropertyReference("userService", "userService");




        // 注册 bean
        listableFactory.registerBeanDefinition();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
