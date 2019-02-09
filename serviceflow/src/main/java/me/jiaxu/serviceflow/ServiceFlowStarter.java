package me.jiaxu.serviceflow;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 */
public interface ServiceFlowStarter<T, R> {

    /**
     * 启动方法
     *
     * @param t
     * @return
     */
    R apply(T t);

}
