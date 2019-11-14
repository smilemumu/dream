//package com.shizhongcai.common.config.datasource;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * druid数据源监控配置
// */
//@Configuration
//public class DruidConfig {
//
//    /**
//     * 主要实现web监控的配置处理
//     * @return
//     */
//    @Bean
//    public ServletRegistrationBean druidServlet() {
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
//                new StatViewServlet(), "/druid/*");//表示进行druid监控的配置处理操作
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1,192.168.1.186");//白名单
//        servletRegistrationBean.addInitParameter("deny", "192.168.1.185");//黑名单
//        servletRegistrationBean.addInitParameter("loginUsername", "root");//用户名
//        servletRegistrationBean.addInitParameter("loginPassword", "root");//密码
//        servletRegistrationBean.addInitParameter("resetEnable", "false");//是否可以重置数据源
//        return servletRegistrationBean;
//    }
//
//    /**
//     *  监控
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(){
//        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new WebStatFilter());
//        filterRegistrationBean.addUrlPatterns("/*");//所有请求进行监控处理
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");//排除
//        return filterRegistrationBean;
//    }
//
//    /**
//     * 返回数据源
//     * @return
//     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druidDataSource() {
//        return new DruidDataSource();
//    }
//}
