package com.cy.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // 将当前类标记为切面类
@Component // 将当前的对象创建使用维护交由spring容器来维护
public class TimerAspect {

    @Around("execution(* com.cy.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 先记录当前时间
        Long start = System.currentTimeMillis();
        Object result = point.proceed(); // 调用目标方法：login
        // 后记录当前时间
        Long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end-start));
        return result;
    }
}

