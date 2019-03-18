package me.jiaxu.serviceflow.common.util;

import me.jiaxu.serviceflow.common.ExceptionEnum;
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

    public static void error(String loggerName, ExceptionEnum exception) {
        Logger logger = Logger.getLogger(loggerName);

        String formattedMessage = exception.getDesc();
        logger.error(formattedMessage);
    }

    public static void error(String loggerName, ExceptionEnum exception, Object... param) {
        Logger logger = Logger.getLogger(loggerName);

        String formattedMessage = String.format(exception.getDesc(), param);
        logger.error(formattedMessage);
    }
}
