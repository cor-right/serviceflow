package me.jiaxu.serviceflow.common;

import java.io.Serializable;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 */
public class ServiceFlowException extends Exception implements Serializable {

    /** 错误 */
    private ExceptionEnum error;

    /** 错误码 */
    private String errorCode;

    /** 错误文案 */
    private String errorMessage;

    /** 错误描述 */
    private String errorDesc;

    public ServiceFlowException() {

    }

    public ServiceFlowException(ExceptionEnum error) {
        super();
        this.error = error;
    }


}
