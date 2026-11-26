package com.d4xaris.bookstore.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.d4xaris.bookstore.exception.AlreadyExistException;
import com.d4xaris.bookstore.exception.BusinessRuleException;
import com.d4xaris.bookstore.exception.NotFoundException;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.d4xaris.bookstore.service..*(..))")
    public void serviceLayer() {}

    @Pointcut("execution(* com.d4xaris.bookstore.controller..*(..))")
    public void controllerLayer() {}

    @Around("serviceLayer() || controllerLayer()")
    public Object logExecution(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().getDeclaringType().getSimpleName()
                + "." + pjp.getSignature().getName();

        log.debug("--> {}", method);
        long start = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();
            log.debug("<-- {} ({} ms)", method, System.currentTimeMillis() - start);
            return result;
        } catch (Throwable t) {
            log.debug("<-- {} threw {} ({} ms)", method,
                    t.getClass().getSimpleName(), System.currentTimeMillis() - start);
            throw t;
        }
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logServiceException(JoinPoint jp, Throwable ex) {
        String method = jp.getSignature().getDeclaringType().getSimpleName()
                + "." + jp.getSignature().getName();

        if (ex instanceof NotFoundException
                || ex instanceof AlreadyExistException
                || ex instanceof BusinessRuleException) {
            log.warn("{} failed: {}", method, ex.getMessage());
        } else {
            log.error("{} failed unexpectedly", method, ex);
        }
    }
}