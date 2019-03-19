package me.jiaxu.serviceflow;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     异常处理器
 *     只针对运行过程中的异常进行处理，不对启动时的异常进行处理
 */
public interface ExceptionHandler {

    /**
     * 用户的异常处理逻辑在这里
     *
     * @param ex
     */
    void process(Exception ex);

}
