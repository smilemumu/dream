package com.shizhongcai.business.alipaydemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * @Author shizhongcai
 * @Date 2019/11/19 15:46
 */
@Service
public class DemoService {

    private static final Logger LOG = LoggerFactory.getLogger(DemoService.class);

    @Resource
    private Executor threadPoolTaskExecutor;

    public void execute() {
        LOG.info("{}",threadPoolTaskExecutor);
        threadPoolTaskExecutor.execute(()->{
            LOG.info("1");
        });
    }
}
