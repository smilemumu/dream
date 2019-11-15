package com.shizhongcai.common.config.web;

import com.shizhongcai.common.argument.AesArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author shizhongcai
 * @Date 2019/11/15 11:01
 */
@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(aesArgumentResolver());
    }
    @Bean
    public AesArgumentResolver aesArgumentResolver(){
        return new AesArgumentResolver();
    }
}
