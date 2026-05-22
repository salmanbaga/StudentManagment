package com.Project.Student.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-MM-SS");
    int reqid = ThreadLocalRandom.current().nextInt(1, 999999);

    @Pointcut("execution(* com.Project.Student.Service..*(..))")
    public void serviceLogging() {
    }

    @Around("serviceLogging()")
    public Object loggingMethodExecution(ProceedingJoinPoint pjp) throws Throwable {

        // Get Service Class Name
        String serviceName = pjp.getTarget().getClass().getSimpleName();

        // Get Method Name
        String methodName = pjp.getSignature().getName();

        LocalDateTime startTime = LocalDateTime.now();
        String startTimerStr = startTime.format(FORMATTER);


        // Entry Log
        log.info("===================Start request id {}===================", reqid);
        log.info("Service: {}, Method: {}", serviceName, methodName);
        log.info("startTimer: {}", startTimerStr);
        log.info("======================================");

        Object result = null;
        Throwable exception = null;

        try {
            result = pjp.proceed();
            return result;
        } catch (Throwable throwable1) {
            exception = throwable1;
            throw exception;
        } finally {

            LocalDateTime endTime = LocalDateTime.now();
            String endTimerStr = endTime.format(FORMATTER);

            Duration duration = Duration.between(startTime, endTime);
            long durationMillis = duration.toMillis();

            // Exit Log
            log.info("===================End request id {}===================", reqid);
            log.info("Service: {}, Method: {}", serviceName, methodName);
            if(exception != null) {
                log.error("Status: Failed | exception: {}", exception.getMessage());
            }
            log.info("duration: {}", durationMillis);
            log.info("endTimer: {}", endTimerStr);
            log.info("======================================");
            

        }
    }
}