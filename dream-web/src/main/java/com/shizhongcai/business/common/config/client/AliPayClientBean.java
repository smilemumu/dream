package com.shizhongcai.business.common.config.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author shizhongcai
 * @Date 2020/1/6 16:22
 */

@Component
public class AliPayClientBean {
    @Value("${ALI.PAY.URL}")
    private String URL;
    @Value("${ALI.PAY.APP_ID}")
    private String APP_ID;
    @Value("${ALI.PAY.APP.PRIVATE_KEY}")
    private String APP_PRIVATE_KEY;
    @Value("${ALI.PAY.APP.PUBLIC_KEY}")
    private String APP_PUBLIC_KEY;
    @Value("${ALI.PAY.FORMAT}")
    private String FORMAT;
    @Value("${ALI.PAY.CHARSET}")
    private String CHARSET;
    @Value("${ALI.PAY.SIGN_TYPE}")
    private String SIGN_TYPE;
    //证书路径
    @Value("${ALI.PAY.alipayCertPublicKey_RSA2Path}")
    private String alipayCertPublicKey_RSA2Path;
    @Value("${ALI.PAY.appCertPublicKeyPath}")
    private String appCertPublicKeyPath;
    @Value("${ALI.PAY.alipayRootCertPath}")
    private String alipayRootCertPath;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getAPP_PRIVATE_KEY() {
        return APP_PRIVATE_KEY;
    }

    public void setAPP_PRIVATE_KEY(String APP_PRIVATE_KEY) {
        this.APP_PRIVATE_KEY = APP_PRIVATE_KEY;
    }

    public String getFORMAT() {
        return FORMAT;
    }

    public void setFORMAT(String FORMAT) {
        this.FORMAT = FORMAT;
    }

    public String getCHARSET() {
        return CHARSET;
    }

    public void setCHARSET(String CHARSET) {
        this.CHARSET = CHARSET;
    }

    public String getSIGN_TYPE() {
        return SIGN_TYPE;
    }

    public void setSIGN_TYPE(String SIGN_TYPE) {
        this.SIGN_TYPE = SIGN_TYPE;
    }

    public String getAlipayCertPublicKey_RSA2Path() {
        return alipayCertPublicKey_RSA2Path;
    }

    public void setAlipayCertPublicKey_RSA2Path(String alipayCertPublicKey_RSA2Path) {
        this.alipayCertPublicKey_RSA2Path = alipayCertPublicKey_RSA2Path;
    }

    public String getAppCertPublicKeyPath() {
        return appCertPublicKeyPath;
    }

    public void setAppCertPublicKeyPath(String appCertPublicKeyPath) {
        this.appCertPublicKeyPath = appCertPublicKeyPath;
    }

    public String getAlipayRootCertPath() {
        return alipayRootCertPath;
    }

    public void setAlipayRootCertPath(String alipayRootCertPath) {
        this.alipayRootCertPath = alipayRootCertPath;
    }

    public String getAPP_PUBLIC_KEY() {
        return APP_PUBLIC_KEY;
    }

    public void setAPP_PUBLIC_KEY(String APP_PUBLIC_KEY) {
        this.APP_PUBLIC_KEY = APP_PUBLIC_KEY;
    }
}
