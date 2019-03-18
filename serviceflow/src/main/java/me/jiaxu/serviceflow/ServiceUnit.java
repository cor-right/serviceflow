package me.jiaxu.serviceflow;

import me.jiaxu.serviceflow.annotation.engine.PrototypeScope;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     工作单元
 *     注意：这里设置了可继承的 @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) 注解，强制执行原型模式
 */
@PrototypeScope
public interface ServiceUnit {

    /**
     * 前置处理
     */
    default void before() {}

    /**
     * 后置处理
     */
    default void after() {}

    /**
     * 业务逻辑
     */
    void process();

}
