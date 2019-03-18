package me.jiaxu.serviceflow.common.util;

import me.jiaxu.serviceflow.common.ExceptionEnum;
import me.jiaxu.serviceflow.common.constant.LoggerConstants;
import me.jiaxu.serviceflow.model.exception.ServiceFlowEngineRuntimeException;
import me.jiaxu.serviceflow.model.exception.ServiceFlowEngineStartException;
import org.apache.log4j.Logger;


/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *      日志工具类
 */
public class LoggerUtils {

    private static final String DEBUG_PREFIX = "[SERVICE FLOW ENGINE]: ";

    // info

    public static void info(String loggerName, String message) {
        Logger logger = Logger.getLogger(loggerName);
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public static void info(String loggerName, String message, Object... param) {
        Logger logger = Logger.getLogger(loggerName);

        if (logger.isInfoEnabled()) {
            String formattedMessage = String.format(message, param);
            logger.info(formattedMessage);
        }
    }

    // debug

    public static void debug(String loggerName, String message) {
        Logger logger = Logger.getLogger(loggerName);
        if (logger.isDebugEnabled()) {
            logger.info(DEBUG_PREFIX + message);
        }
    }

    public static void debug(String loggerName, String message, Object... param) {
        Logger logger = Logger.getLogger(loggerName);

        if (logger.isDebugEnabled()) {
            String formattedMessage = DEBUG_PREFIX + String.format(message, param);
            logger.info(formattedMessage);
        }
    }

    // warn

    public static void warn(String loggerName, String message) {
        Logger logger = Logger.getLogger(loggerName);
        logger.warn(message);
    }

    public static void warn(String loggerName, String message, Object... param) {
        Logger logger = Logger.getLogger(loggerName);

        String formattedMessage = String.format(message, param);
        logger.warn(formattedMessage);
    }

    // error

    public static void error(String loggerName, String message) {
        Logger.getLogger(loggerName).error(message);
    }

    public static void error(String loggerName, String message, Object... param) {
        Logger logger = Logger.getLogger(loggerName);

        String formattedMessage = String.format(message, param);
        logger.error(formattedMessage);
    }

    public static void error(String loggerName, ServiceFlowEngineStartException exception) {
        Logger logger = Logger.getLogger(loggerName);
        Logger defaultError = Logger.getLogger(LoggerConstants.DEFAULT_ERROR);

        String formattedMessage = exception.getErrorDesc();
        logger.error(formattedMessage);

        defaultError.error(formattedMessage);
        defaultError.error(exception.getMessage());
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            defaultError.error(stackTraceElement.toString());
        }

    }

    public static void error(String loggerName, ServiceFlowEngineStartException exception, Object... param) {
        Logger logger = Logger.getLogger(loggerName);
        Logger defaultError = Logger.getLogger(LoggerConstants.DEFAULT_ERROR);

        String formattedMessage = String.format(exception.getErrorDesc(), param);
        logger.error(formattedMessage);

        defaultError.error(formattedMessage);
        defaultError.error(exception.getMessage());
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            defaultError.error(stackTraceElement.toString());
        }
    }

    public static void error(String loggerName, ServiceFlowEngineRuntimeException exception) {
        Logger logger = Logger.getLogger(loggerName);
        Logger defaultError = Logger.getLogger(LoggerConstants.DEFAULT_ERROR);

        String formattedMessage = exception.getErrorDesc();
        logger.error(formattedMessage);

        defaultError.error(formattedMessage);
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            defaultError.error(stackTraceElement.toString());
        }



    }

    public static void error(String loggerName, ServiceFlowEngineRuntimeException exception, Object... param) {
        Logger logger = Logger.getLogger(loggerName);
        Logger defaultError = Logger.getLogger(LoggerConstants.DEFAULT_ERROR);

        String formattedMessage = String.format(exception.getErrorDesc(), param);
        logger.error(formattedMessage);

        defaultError.error(formattedMessage);
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            defaultError.error(stackTraceElement.toString());
        }
    }
}
