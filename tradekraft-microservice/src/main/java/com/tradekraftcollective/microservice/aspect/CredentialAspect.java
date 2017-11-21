package com.tradekraftcollective.microservice.aspect;

import com.tradekraftcollective.microservice.model.Credentials;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by brandonfeist on 11/20/17.
 */
@Slf4j
@Aspect
@Component
public class CredentialAspect {
    @Inject
    HttpServletRequest httpServletRequest;

    @Inject
    Credentials credentials;

    @Pointcut("@target(org.springframework.web.bind.annotation.RestController) && within(com.tradekraftcollective.microservice..*) && !execution(* setResponseHeader(..))")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void initCredential() {
        credentials.setAuthorization(httpServletRequest.getHeader("authorization"));
    }
}
