package com.shizhongcai.business.common.config.aspect;

import com.alibaba.fastjson.JSON;
import com.shizhongcai.business.common.config.filter.MDCFilter;
import com.shizhongcai.business.common.domain.data.AesBaseParams;
import com.shizhongcai.business.common.domain.enums.ErrorCodesEnum;
import com.shizhongcai.business.common.domain.vo.BaseReqVo;
import com.shizhongcai.business.common.domain.vo.BaseRspVo;
import com.shizhongcai.business.common.exception.BaseException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;

/**
 * @Author shizhongcai
 * @Date 2019/11/15 11:13
 * Aspect先执行是随机的，如果需要定义顺序，可以使用@Order注解修饰Aspect类。值越小，优先级越高。
 */
@Order(1)
@Component
@Aspect
public class WebLogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(MDCFilter.class);

    // MDC过滤器注册
    @Bean
    public FilterRegistrationBean mdcFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new MDCFilter());
        registration.addUrlPatterns("/*");
        registration.setName("MDCFilter");
        //设置???
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }




    //========================↓↓↓ controller AOP切面日志打印 ↓↓↓========================
    @Pointcut("execution(* com.shizhongcai..*Controller.*(..))")
    public void pointService(){

    }

    @Around("pointService()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        Instant end;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        int index = -1;
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            Object object = joinPoint.getArgs()[i];
            if (object instanceof AesBaseParams) {
                index = i;
                break;
            }else if(object instanceof BaseReqVo){
                index = i;
                break;
            }else{
                index = i;
                break;
            }
        }
        String method = joinPoint.getSignature().getName();
        //此处index = -1的时候表示无参数，此时调用joinPoint.getArgs[index]会报错空指针异常
//        LOG.info("请求类型{}，请求方法{},入参 {}",request.getMethod(),method,index==-1?"为空":JSON.toJSONString(joinPoint.getArgs()[index]));
        //执行方法本身
        //此处还可以加锁，防止接口重复调用
        Object obj;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOG.error("执行出错：", throwable);
            BaseRspVo rspVo;
            if (BaseException.class.isAssignableFrom(throwable.getClass())) {
                if (throwable instanceof BaseException) {
                    rspVo = BaseRspVo.fail(((BaseException)throwable).getCode(), ((BaseException) throwable).getMsg());
                } else {
                    rspVo = new BaseRspVo(ErrorCodesEnum.SYS_ERROR);
                }
            } else {
                rspVo = new BaseRspVo(ErrorCodesEnum.SYS_ERROR);
            }
            return rspVo;
        }
        end = Instant.now();
        long costTime = Duration.between(start,end).toMillis();
        LOG.info("{}出参:{},耗时{}(mills)",method, JSON.toJSONString(obj),costTime);
        return obj;
    }
}
