package com.mrdios.foodie.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * 方法执行耗时切面
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Slf4j
@Aspect
@Configuration
public class RuntimeAspect {

    /**
     * service 层方法切入点
     */
    @Pointcut("execution(* com.mrdios.foodie.service..*.*(..))")
    public void service() {

    }

    @Around("service()")
    public Object recordRunTime(ProceedingJoinPoint pjp) throws Throwable {
        log.info("========= 开始执行{}.{} =========", pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName());
        long begin = System.currentTimeMillis();
        Object result = pjp.proceed();
        long takeTime = System.currentTimeMillis() - begin;
        if (takeTime > 3000) {
            log.error("========= 执行结束，耗时：{} 毫秒 =========", takeTime);
        } else if (takeTime > 2000) {
            log.warn("========= 执行结束，耗时：{} 毫秒 =========", takeTime);
        } else {
            log.info("========= 执行结束，耗时：{} 毫秒 =========", takeTime);
        }
        return result;
    }
}
