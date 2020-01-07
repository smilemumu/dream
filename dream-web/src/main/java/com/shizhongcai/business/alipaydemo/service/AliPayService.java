package com.shizhongcai.business.alipaydemo.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.shizhongcai.business.common.config.client.AliPayClientBean;
import com.shizhongcai.business.alipaydemo.domain.vo.AliScanPayCancelTradeReqVo;
import com.shizhongcai.business.alipaydemo.domain.vo.AliScanPayTradeQueryReqVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author shizhongcai
 * @Date 2019/12/31 15:36
 */
@Service
public class AliPayService {

    @Resource
    private AliPayClientBean aliPayClientBean;
    @Resource
    private DefaultAlipayClient defaultAlipayClient;

    private static final Logger logger = LoggerFactory.getLogger(AliPayService.class);

    public String getQrCode(String outTradeNo) {
        AlipayTradePrecreateRequest preCreateRequest = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setSubject("测试");
        model.setTotalAmount("8.88");
        model.setStoreId("");
        model.setTimeoutExpress("5m");
        model.setOutTradeNo(outTradeNo);
        preCreateRequest.setBizModel(model);
        preCreateRequest.setNotifyUrl("http://1b51z57431.51mypc.cn/dream/pay/ali/scan_pay/callback");
        try {
            AlipayTradePrecreateResponse preCreateResponse = defaultAlipayClient.certificateExecute(preCreateRequest);
            if(preCreateResponse.isSuccess()){
                System.out.println("调用成功");
                return preCreateResponse.getQrCode();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String scanCallBack(Map<String, String> params) {
        boolean verifyResult = false;
        try {
            verifyResult = AlipaySignature.rsaCertCheckV1(params, aliPayClientBean.getAlipayCertPublicKey_RSA2Path(), "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (verifyResult) {
            // TODO 请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理
            System.out.println("scanCallBack 验签成功succcess");
            logger.info("阿里扫码结果回调：{}", JSON.toJSONString(params));
            return "success";
        } else {
            System.out.println("scanCallBack 验签名失败");
            // TODO
            return "failure";
        }
    }

    public AlipayTradeQueryResponse loopTradeQuery(AliScanPayTradeQueryReqVo reqVo) {
        AlipayTradeQueryRequest tradeQueryRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel tradeQueryModel = new AlipayTradeQueryModel();
        tradeQueryModel.setOutTradeNo(reqVo.getOutTradeNo());
        tradeQueryRequest.setBizModel(tradeQueryModel);
        AlipayTradeQueryResponse tradeQueryResponse = null;
        try {
            tradeQueryResponse = defaultAlipayClient.certificateExecute(tradeQueryRequest);
            if(tradeQueryResponse.isSuccess()){
                System.out.println("调用成功");
                if("10000".equals(tradeQueryResponse.getCode())){
                    if("TRADE_SUCCESS".equals(tradeQueryResponse.getTradeStatus())){

                    }else if("TRADE_CLOSED".equals(tradeQueryResponse.getTradeStatus())){

                    }else{
                        //....其他状态
                    }
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return tradeQueryResponse;
    }

    public AlipayTradeCancelResponse cancelTrade(AliScanPayCancelTradeReqVo reqVo){
        //撤销交易
        AlipayTradeCancelRequest tradeCancelRequest  = new AlipayTradeCancelRequest();
        AlipayTradeCancelModel tradeCancelModel = new AlipayTradeCancelModel();
        tradeCancelModel.setOutTradeNo(reqVo.getOutTradeNo());
        tradeCancelRequest.setBizModel(tradeCancelModel);
        AlipayTradeCancelResponse tradeCancelResponse = null;
        try {
            tradeCancelResponse = defaultAlipayClient.certificateExecute(tradeCancelRequest);
            if(tradeCancelResponse.isSuccess()){
                System.out.println("调用成功");
                if("10000".equals(tradeCancelResponse.getCode())){
                    if ("refund".equals(tradeCancelResponse.getAction())) {

                        //refund：交易已支付，触发交易退款动作；

                    }else if("close".equals(tradeCancelResponse.getAction())){
                        //close：交易未支付，触发关闭交易动作，无退款；
                    }else{
                        //未返回：未查询到交易，或接口调用失败；
                    }
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return tradeCancelResponse;
    }
}
