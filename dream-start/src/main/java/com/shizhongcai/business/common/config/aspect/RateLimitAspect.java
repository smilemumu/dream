package com.shizhongcai.business.common.config.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.shizhongcai.business.common.annotation.RateLimit;
import com.shizhongcai.business.common.domain.enums.ErrorCodesEnum;
import com.shizhongcai.business.common.exception.BaseException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 令牌桶切面，桶中无令牌进行服务降级
 *
 */
@Order(2)
@Component
@Scope
@Aspect
public class RateLimitAspect {

    //用来存放不同接口的RateLimiter令牌桶(key为接口名称，value为RateLimiter)
    //ConcurrentHashMap线程安全
    private ConcurrentHashMap<String, RateLimiter> map = new ConcurrentHashMap<>();

    //令牌桶
    private RateLimiter rateLimiter;

    @Pointcut("@annotation(com.shizhongcai.business.common.annotation.RateLimit)")  //切入点是注解@RateLimit
    public void serviceLimit() {
        //该注解实现的方法体（新建了公用方法体，方面后面写增强）
    }


    @Around("serviceLimit()")   //环绕增强，方法体是logPointCut
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取注解信息
        RateLimit annotation = method.getAnnotation(RateLimit.class);
        double limitNum = annotation.limitNum(); //获取注解每秒加入桶中的token

        //获取方法名
        String methodName = signature.getName();

        //根据方法名获取其rateLimiter令牌桶
        if(map.containsKey(methodName)){
            rateLimiter = map.get(methodName);
        }else {
            //如果该令牌桶未被初始化，则先初始化令牌桶
            map.put(methodName, RateLimiter.create(limitNum));
            rateLimiter = map.get(methodName);
        }
        Object result;
        //尝试获取令牌
        if (rateLimiter.tryAcquire()) {
            //令牌获取成功，顺利执行方法
            result = joinPoint.proceed();
        } else {
            //拒绝了请求（服务降级）
            throw new BaseException(ErrorCodesEnum.REQUEST_TOO_FAST);
        }
        return result;
    }

}