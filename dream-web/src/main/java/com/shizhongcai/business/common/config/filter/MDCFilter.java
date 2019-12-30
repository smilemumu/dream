package com.shizhongcai.business.common.config.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author shizhongcai
 * @Date 2019/11/15 11:15
 */
@Component
public class MDCFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(MDCFilter.class);

    public static final String MDC_ID = "reqId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean mdcFlag = false;
        try {
            MDC.put(MDC_ID, UUID.randomUUID().toString());
            mdcFlag = true;
        } catch (Throwable e) {
            LOG.error("MDC put error", e);
        }
        try {
            String path = ((HttpServletRequest)servletRequest).getRequestURL().toString();
            LOG.info("request path:" + path);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (mdcFlag) {
                try {
                    MDC.remove(MDC_ID);
                } catch (Throwable e) {
                    LOG.error("MDC remove error", e);
                }
            }
        }
    }

    @Override
    public void destroy() {
    }
}
