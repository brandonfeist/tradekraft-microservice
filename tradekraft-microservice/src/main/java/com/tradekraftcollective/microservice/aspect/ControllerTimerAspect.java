package com.tradekraftcollective.microservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.inject.Inject;

/**
 * Created by brandonfeist on 11/6/17.
 */
@Aspect
@Slf4j
@Component
public class ControllerTimerAspect {
    @Inject
    StopWatch stopWatch;

    @Pointcut("@target(org.springframework.web.bind.annotation.RestController) && within(com.tradekraftcollective.microservice..*) && !execution(* setResponseHeader(..))")
    public void controllerMethod() {}

    @Around("controllerMethod()")
    public Object logTimer(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().toShortString();

        stopWatch.start(methodName);
        Object returnValue;

        try {
            log.info("Calling {}", methodName);
            returnValue = proceedingJoinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info(stopWatch.prettyPrint());
        }

        return returnValue;
    }
}
