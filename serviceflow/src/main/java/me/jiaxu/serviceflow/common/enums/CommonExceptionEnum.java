package me.jiaxu.serviceflow.common.enums;

/**
 * Created by jiaxu.zjx on 2019/3/19
 * Description:
 */
public enum CommonExceptionEnum {

    /** 默认异常 */
    DEFAULT_ERROR("defaultError", "微服务工作流组件异常"),

    // 自动注册异常

    CLASS_NOT_EXIST("classNotExist", "被加载的类没有找到"),

    BEAN_NOT_EXIST("beanNotExist", "上下文中不存在指定的Bean"),

    BEAN_CAST_EXCEPTION("beanCastException", "Bean进行类型转换时发生异常"),

    ;


    /** 错误码 */
    private String errorCode;

    /** 错误描述 */
    private String errorDesc;

    CommonExceptionEnum(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    /**
     * 根据code获得枚举
     *
     * @param code
     * @return
     */
    public static CommonExceptionEnum getEnumByCode(String code) {
        for (CommonExceptionEnum commonExceptionEnum : CommonExceptionEnum.values()) {
            if (commonExceptionEnum.errorCode.equals(code)) {
                return commonExceptionEnum;
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
