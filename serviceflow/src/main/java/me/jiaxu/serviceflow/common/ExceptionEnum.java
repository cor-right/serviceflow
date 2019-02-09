package me.jiaxu.serviceflow.common;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     错误枚举
 */
public enum ExceptionEnum {

    DEFAULT_ERROR("defaultError", "微服务流引擎异常", "微服务流引擎异常"),

    MISSED_SERVICE_UNIT("missedServiceUnit", "上下文中不存在指定的服务单元", "上下文中不存在指定的服务单元"),

    DUPLICATE_OUT("duplicateOut", "当前服务流出现了多个出口", "一个微服务流中只能存在一个 @Out 成员变量"),

    SUBSCRIBE_BEFORE_PUBLISH("subscribeBeforePublish", "被订阅的元素尚未进行发布", "当前 @SubScribe 的元素尚未进行 @Publish"),

    ;


    /** 错误码 */
    private String errorCode;

    /** 错误文案 */
    private String errorMessage;

    /** 错误描述 */
    private String errorDesc;

    ExceptionEnum(String errorCode, String errorMessage, String errorDesc) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
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

}
