package springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {


    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* springdemo.controller.*..*(..))")
    private void forControllerPackage(){}

    @Pointcut("execution(* springdemo.service.*..*(..))")
    private void forServicePackage(){}

    @Pointcut("execution(* springdemo.dao.*..*(..))")
    private void forDaoPackage(){}


    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow(){}


    @Before("forAppFlow()")
    private void before(JoinPoint joinPoint){
        String method = joinPoint.getSignature().toShortString();
        logger.info("====>> in @Before: calling method: " +method);

        Object[] args = joinPoint.getArgs();

         for (Object tempArg : args ) {
             logger.info("=====>> arguments: " +tempArg);
         }
     }


     @AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
    private void afterReturning(JoinPoint joinPoint, Object theResult){

        String method = joinPoint.getSignature().toShortString();
         logger.info("====>> in @AfterReturning: from method: " +method);

         logger.info("=====>> result: " +theResult);
     }
}
