package com.abbtech.aspect;

import com.abbtech.annotation.LogIgnore;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllers() {}

    @Around("restControllers()")
    public Object logApi(ProceedingJoinPoint joinPoint) throws Throwable {

        logRequest(joinPoint);
        Object result = joinPoint.proceed();
        logResponse(joinPoint);
        return result;

    }

    private void logRequest(ProceedingJoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().toShortString();

        Object[] args = joinPoint.getArgs();

        log.info("API REQUEST: {}", methodName);

        for (Object arg : args) {
            log.info("Request body: {}", filterIgnoredFields(arg));
        }
    }

    private void logResponse(Object response) {
        log.info("⬅️ API RESPONSE: {}", filterIgnoredFields(response));
    }

    private Object filterIgnoredFields(Object obj) {

        if (obj == null) return null;

        Class<?> clazz = obj.getClass();

        Map<String, Object> result = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {

            field.setAccessible(true);

            if (field.isAnnotationPresent(LogIgnore.class)) {
                result.put(field.getName(), "***IGNORED***");
            } else {
                try {
                    result.put(field.getName(), field.get(obj));
                } catch (IllegalAccessException e) {
                    result.put(field.getName(), "ACCESS_ERROR");
                }
            }
        }

        return result;
    }


}
