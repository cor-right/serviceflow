package me.jiaxu.serviceflow.model.exception;

import me.jiaxu.serviceflow.common.enums.CommonExceptionEnum;
import me.jiaxu.serviceflow.common.enums.ExceptionEnum;

/**
 * Created by jiaxu.zjx on 2019/3/19
 * Description:
 */
public class ServiceFlowEngineCommonException extends Exception {

    /** */
    private String errorCode;

    /** */
    private String errorDesc;

    /** */
    private CommonExceptionEnum errorEnum;

    /**
     * default
     *
     * ${@link ExceptionEnum#DEFAULT_ERROR}
     */
    public ServiceFlowEngineCommonException() {
        this.errorCode = CommonExceptionEnum.DEFAULT_ERROR.getCode();
        this.errorDesc = CommonExceptionEnum.DEFAULT_ERROR.getDesc();
        this.errorEnum = CommonExceptionEnum.DEFAULT_ERROR;
    }

    /**
     * specific
     *
     * @param exceptionEnum ${@link ExceptionEnum}
     */
    public ServiceFlowEngineCommonException(CommonExceptionEnum exceptionEnum) {
        this.errorCode = exceptionEnum.getCode();
        this.errorDesc = exceptionEnum.getDesc();
        this.errorEnum = exceptionEnum;
    }

    /** */
    public String getErrorCode() {
        return errorCode;
    }

    /** */
    public String getErrorDesc() {
        return errorDesc;
    }

    /** */
    public CommonExceptionEnum getErrorEnum() {
        return errorEnum;
    }

}
