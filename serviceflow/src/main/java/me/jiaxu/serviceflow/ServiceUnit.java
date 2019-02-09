package me.jiaxu.serviceflow;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     工作单元
 */
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
