package me.jiaxu.serviceflow.common;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     错误枚举
 */
public enum ExceptionEnum {

    /** 默认异常 */
    DEFAULT_ERROR("defaultError", "微服务流引擎异常"),

    // ------ 流程异常 ------

    /** 空流程 */
    NO_FLOW_ORDER_MANAGER_DEFINED("noFlowOrderManagerDefined", "当前系统使用了工作流流程的方式进行服务，但未定义任何工作流流程" ),

    /** 流程未找到 */
    MISSED_FLOW_ORDER_MANAGER("missedFlowOrderManager", "上下文中不存在指定的工作流流程"),

    /** 流程中必须有 @Out */
    NONE_OUT_FOUND("noneOutFieldFound", "当前服务流中没有声明出参 @out"),

    /** 一个流程最多一个 @Out */
    DUPLICATE_OUT("duplicateOut", "当前服务流出现了多个出口"),



    // ------ 工作单元异常 ------

    /** 工作单元未找到 */
    MISSED_SERVICE_UNIT("missedServiceUnit", "上下文中不存在指定的服务单元"),



    // ------ 属性异常 ------

    /** 成员变量标注了多于一个的引擎提供的注解 */
    TOO_MUCH_DECLARED_ANNOTATIONS("tooMuchDeclaredAnnotations", "同一成员变量最多标注一种引擎提供的注解"),

    /** SubScribe 在 Publish 之前被执行 */
    SUBSCRIBE_BEFORE_PUBLISH("subscribeBeforePublish", "被订阅的元素尚未进行发布"),

    ;


    /** 错误码 */
    private String errorCode;

    /** 错误描述 */
    private String errorDesc;

    ExceptionEnum(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    /**
     * 根据code获得枚举
     *
     * @param code
     * @return
     */
    public static ExceptionEnum getEnumByCode(String code) {
        for (ExceptionEnum ExceptionEnum : ExceptionEnum.values()) {
            if (ExceptionEnum.errorCode.equals(code)) {
                return ExceptionEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return this.errorCode;
    }

    public String getDesc() {
        return this.errorDesc;
    }

}
