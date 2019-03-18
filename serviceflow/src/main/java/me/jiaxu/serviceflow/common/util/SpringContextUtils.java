package me.jiaxu.serviceflow.common.util;

import me.jiaxu.serviceflow.ServiceUnit;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     spring 上下文工具类
 */
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 获取指定的 bean
     *
     * 在原生方法上添加了自动转换的功能
     *
     * @param name 实现类名
     * @param type 接口类型
     * @return bean
     */
    public static <T> T getBean(String name, Class<T> type) {
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
    public static ServiceUnit getServiceUnit(String name) {
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
    public static <T> Map<String, T> getBeansForType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

}
