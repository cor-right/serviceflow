package me.jiaxu.serviceflow;


/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     工作流启动器，即引擎本身
 *     注意：这里设置了可继承的 @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) 注解，强制执行原型模式
 */
public interface ServiceFlowStarter<T, R> {

    /**
     * 设置领域限定规则
     *
     * @param rule 规则
     */
    void setDomainConstraintRule(String rule);

    /**
     * 设定用户业务异常处理器
     *
     * @param exceptionHandler
     */
    void setExceptionHandler(ExceptionHandler exceptionHandler);

    /**
     * 启动方法
     *
     * @param t 请求入参
     * @return response
     */
    default R apply(T t) {
        return null;
    }

    /**
     * 启动方法
     *
     * @param t 请求入参
     * @param manager 工作流流程定义实现类
     * @return response
     */
    default R apply(T t, FlowOrderManager manager) throws Exception {
        return null;
    }

}
