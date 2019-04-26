package com.rexen.rest.framework.log;

import com.alibaba.fastjson.JSONObject;
import com.rexen.rest.annotation.FunctionLog;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.javatuples.Quartet;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author: GavinHacker
 * @description:
 * @date: Created in 上午10:05 18/5/21
 * @modifiedBy:
 * @deprecated 废弃，使用框架本身的日志记录拦截
 */
//@Component
//@Aspec
public class FunctionLogCapturer {

    Logger logger = Logger.getLogger(FunctionLogCapturer.class);

    @Around("functionLoggingAspect()")
    public Object normalAdvice(ProceedingJoinPoint point) throws Throwable {
        logger.debug("Start normal advice ...");
        Object result = null;
        Long start = System.currentTimeMillis();
        result = point.proceed();
        Long end = System.currentTimeMillis();
        Long time = end - start;

        Quartet<String, String, String, Object> logContent = null;
        try {
            logContent = getFunctionLoggingContent(point);
        } catch (ClassNotFoundException e1) {
            logger.error("Error occurred while logging normal advice", e1);
        }
        if(logContent != null) {
            printLoggingContent(point, logContent, time);
            insertFunctionLog(logContent);
        }

        logger.debug("Finish normal advice ...");
        return result;
    }

    @AfterThrowing(pointcut = "functionLoggingAspect()", throwing = "e")
    public void exceptionAdvice(JoinPoint point, Throwable e) {
        logger.debug("Start exception advice ...");
        Quartet<String, String, String, Object> logContent = null;
        try {
            logContent = getFunctionLoggingContent(point);
        } catch (ClassNotFoundException e1) {
            logger.error("Error occurred while logging throwing advice", e1);
        }
        if(logContent != null) {
            printLoggingContent(point, logContent, 0L);
            insertFunctionLog(logContent);
        }
        logger.debug("Finish exception advice ...");
    }

    public Quartet<String, String, String, Object> getFunctionLoggingContent(JoinPoint point) throws ClassNotFoundException {
        Class target = Class.forName(point.getTarget().getClass().getName());
        Method method = Arrays.stream(target.getMethods()).filter(x -> x.getName().equals(point.getSignature().getName())).findFirst().orElse(null);
        return method == null ? null : new Quartet<>(method.getAnnotation(FunctionLog.class).module(),
                method.getAnnotation(FunctionLog.class).operation(),
                method.getAnnotation(FunctionLog.class).extension(),
                point.getArgs());
    }

    public void printLoggingContent(JoinPoint point, Quartet loggingContent, Long duration){
        String content = String.format("ClassName: %s, method: %s, time: %s Millis, module: %s, operation: %s, extends: %s, json: %s",
                point.getTarget().getClass().getName(),
                point.getSignature().getName(),
                duration,
                loggingContent.getValue0(),
                loggingContent.getValue1(),
                loggingContent.getValue2(),
                JSONObject.toJSONString(loggingContent.getValue3()));
        logger.info(content);
    }

    public void insertFunctionLog(Quartet<String, String, String, Object> content){
        SystemOperationLog systemOperationLog = new SystemOperationLog();
        String mId = UUID.randomUUID().toString();
        systemOperationLog.setId(mId);

        try {
            systemOperationLog.setClientIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            logger.warn("获取ip异常".concat(e.getMessage()));
        }
        systemOperationLog.setModule(content.getValue0());
        systemOperationLog.setOperationType(content.getValue1());
        systemOperationLog.setUserId("admin");
        systemOperationLog.setUserName("管理员");
        ////////////systemOperationLogService.insert(systemOperationLog);
        logger.warn("content"+content.getValue0());
        SystemOperationParam systemOperationParam = new SystemOperationParam();
        systemOperationParam.setId(UUID.randomUUID().toString());
        systemOperationParam.setLogId(mId);
        systemOperationParam.setRequestParam(JSONObject.toJSONString(content.getValue3()));
        /////////////systemOperationParamService.insert(systemOperationParam);
        logger.warn("content"+content.getValue3());
    }

    @Pointcut("@annotation(com.rexen.rest.annotation.FunctionLog)")
    public void functionLoggingAspect() {
    }
}
