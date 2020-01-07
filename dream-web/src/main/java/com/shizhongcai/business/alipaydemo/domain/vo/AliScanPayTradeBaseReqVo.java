package com.shizhongcai.business.alipaydemo.domain.vo;

import lombok.Data;

/**
 * @Author shizhongcai
 * @Date 2020/1/6 20:05
 */
@Data
public class AliScanPayTradeBaseReqVo {
    /**
     * 支付时传入的商户订单号，与 trade_no 必填一个
     */
    private String outTradeNo;
    /**
     * 支付时返回的支付宝交易号，与 out_trade_no 必填一个
     */
    private String tradeNo;
}
