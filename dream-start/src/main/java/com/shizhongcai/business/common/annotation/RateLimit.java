package com.shizhongcai.business.common.annotation;

/**
 * @Author shizhongcai
 * @Date 2019/11/20 14:46
 */
import java.lang.annotation.*;

/**
 * 自定义注解：限流令牌桶注解，用以创建令牌桶以及设定令牌桶大小
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    double limitNum() default 20;  //默认每秒放入桶中20
}
