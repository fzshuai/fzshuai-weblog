package com.fzshuai.aspect;

<<<<<<< HEAD

import org.aopalliance.intercept.Joinpoint;
=======
>>>>>>> d30a2ee (项目第一次提交)
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author 软件二班傅同学
<<<<<<< HEAD
 * @date 2021-01-18 14:26
=======
 * @description TODO
 * @date 2021-02-03 19:29
>>>>>>> d30a2ee (项目第一次提交)
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

<<<<<<< HEAD
    @Pointcut("execution(* com.fzshuai.web.*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

=======
    @Pointcut("execution(* com.fzshuai.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
>>>>>>> d30a2ee (项目第一次提交)
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
<<<<<<< HEAD

=======
>>>>>>> d30a2ee (项目第一次提交)
        logger.info("Request : {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
<<<<<<< HEAD
//        logger.info("-----------doAfter-----------");
=======
        //logger.info("--------doAfter--------");
>>>>>>> d30a2ee (项目第一次提交)
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
<<<<<<< HEAD
            return "RequestLog{" +
=======
            return "{" +
>>>>>>> d30a2ee (项目第一次提交)
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
