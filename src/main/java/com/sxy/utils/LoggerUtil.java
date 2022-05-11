package com.sxy.utils;


import org.apache.log4j.Logger;

/**
 * 打印日志
 */
public class LoggerUtil {
    private static Logger logger = Logger.getLogger(LoggerUtil.class.getName());
    //用例开始
    public static void startTestCase(String sTestCaseId,String sTestCaseName){
        logger.info("==========="+sTestCaseId+"=="+sTestCaseName+":测试用例开始执行"+"===========");
    }
    //用例结束
    public static void endTestCase(String sTestCaseId,String sTestCaseName){
        logger.info("==========="+sTestCaseId+"=="+sTestCaseName+":测试用例执行结束"+"===========");
    }

    public static void info(String message){
        logger.info(message);
    }
    //警告
    public static void warn(String message){
        logger.warn(message);
    }
    //错误信息
    public static void error(Exception e){
        logger.error(e);
    }
    //错误信息
    public static void error(String message){
        logger.error(message);
    }
    //调试信息
    public static void debug(String message){
        logger.debug(message);
    }
}
