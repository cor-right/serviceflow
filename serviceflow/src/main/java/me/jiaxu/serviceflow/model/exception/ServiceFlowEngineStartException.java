package me.jiaxu.serviceflow.model.exception;

import me.jiaxu.serviceflow.common.ExceptionEnum;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     引擎启动时的异常
 */
public class ServiceFlowEngineStartException extends Exception {

    /** */
    private String errorCode;

    /** */
    private String errorDesc;

    /** */
    private ExceptionEnum errorEnum;

    /**
     * default
     *
     * ${@link ExceptionEnum#DEFAULT_ERROR}
     */
    public ServiceFlowEngineStartException() {
        this.errorCode = ExceptionEnum.DEFAULT_ERROR.getCode();
        this.errorDesc = ExceptionEnum.DEFAULT_ERROR.getDesc();
        this.errorEnum = ExceptionEnum.DEFAULT_ERROR;
    }

    /**
     * specific
     *
     * @param exceptionEnum ${@link ExceptionEnum}
     */
    public ServiceFlowEngineStartException(ExceptionEnum exceptionEnum) {
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
    public ExceptionEnum getErrorEnum() {
        return errorEnum;
    }
}
