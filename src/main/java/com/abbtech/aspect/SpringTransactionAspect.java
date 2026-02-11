package com.abbtech.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SpringTransactionAspect {
    @Pointcut("@annotation(com.abbtech.annotation.SpringTransactionAnnotation)")
    public void transactional(){

    }
    @Before("transactional()")
    public void beforeTransaction(){
        System.out.println("""
                Get Connection from pool ,Open connection , Start Transaction...
                """);
    }

    @AfterReturning(pointcut = "transactional()" , returning = "result")
    public void afterTransaction(JoinPoint joinPoint,Object result){
        System.out.println("""
                Commit transaction END ,
                Close connection , Return connection to pool
        """);
    }

    @AfterThrowing(pointcut = "transactional()" , throwing = "ex")
    public void rollbackTransaction(JoinPoint joinPoint,Exception ex){
        if (ex instanceof RuntimeException exception){
            System.out.println("""
                Rollback transaction
        """);
        }


    }
}
