package com.shizhongcai.business.alipaydemo.controller;

import com.shizhongcai.business.common.annotation.RateLimit;
import com.shizhongcai.business.alipaydemo.domain.entity.DemoEntity;
import com.shizhongcai.business.alipaydemo.domain.vo.AesTestReqVo;
import com.shizhongcai.business.common.annotation.AesJson;
import com.shizhongcai.business.common.domain.vo.BaseRspVo;
import com.shizhongcai.business.alipaydemo.domain.vo.ValidatorReqVo;
import com.shizhongcai.business.alipaydemo.service.DemoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author shizhongcai
 * @Date 2019/11/15 16:25
 */
@RestController
@RequestMapping("/demo")
public class DemoController {


    @Resource
    private DemoService demoService;

    /**
     * GET测试接口
     * @return
     */
    @GetMapping(value = "/testGetNoParam")
    public BaseRspVo testGet(){
        DemoEntity demoEntity  = new DemoEntity();
        demoEntity.setName("123");
        return new BaseRspVo<>(demoEntity);
    }

    /**
     * GET测试接口
     * @return
     */
    @PostMapping(value = "/testAesJson")
    public BaseRspVo testAesJson(@AesJson AesTestReqVo reqVo){
        String name = reqVo.getName();
        return new BaseRspVo<>(reqVo);
    }

    /**
     * Sring自定义线程池
     * @return
     */
    @PostMapping(value = "/testPool")
    public void testPool(){
        demoService.execute();
    }


    /**
     * 测试接口限流
     * limitNum = 1 表示每秒只能放过1个请求
     * @return
     */
    @RateLimit(limitNum = 1)
    @PostMapping(value = "/testRateLimit")
    public BaseRspVo testRateLimit(){
        return new BaseRspVo<>(Arrays.asList("1","2"));
    }

    /**
     * 测试参数校验
     * @param reqVo
     * @return
     */
    @PostMapping(value = "/testValidate")
    public BaseRspVo testValidate(@Validated @RequestBody ValidatorReqVo reqVo){
        System.out.println(1);
        return new BaseRspVo<>(reqVo);
    }

}
