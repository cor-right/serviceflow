package me.jiaxu.serviceflow;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     动态定义 springBean
 */
@Component
public class BeanDynamicDefinition implements BeanDefinitionRegistryPostProcessor {

    /** */
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public void registryBean(Class<?> beanName) {

    }


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
