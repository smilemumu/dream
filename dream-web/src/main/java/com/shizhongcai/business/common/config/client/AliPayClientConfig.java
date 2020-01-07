package com.shizhongcai.business.common.config.client;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author shizhongcai
 * @Date 2019/12/30 11:30
 */
@Configuration
public class AliPayClientConfig {


    @Resource
    private AliPayClientBean aliPayClientBean;

    @Bean("defaultAlipayClient")
    public DefaultAlipayClient getAlipayClient() throws AlipayApiException {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(aliPayClientBean.getURL());
        certAlipayRequest.setAppId(aliPayClientBean.getAPP_ID());
        certAlipayRequest.setPrivateKey(aliPayClientBean.getAPP_PRIVATE_KEY());
        certAlipayRequest.setFormat(aliPayClientBean.getFORMAT());
        certAlipayRequest.setCharset(aliPayClientBean.getCHARSET());
        certAlipayRequest.setSignType(aliPayClientBean.getSIGN_TYPE());
        //应用公钥证书路径
        certAlipayRequest.setCertPath(aliPayClientBean.getAppCertPublicKeyPath());
        //支付宝公钥证书文件路径
        certAlipayRequest.setAlipayPublicCertPath(aliPayClientBean.getAlipayCertPublicKey_RSA2Path());
        //支付宝CA根证书文件路径
        certAlipayRequest.setRootCertPath(aliPayClientBean.getAlipayRootCertPath());
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        return alipayClient;
    }
}
