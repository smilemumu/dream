package com.shizhongcai.business;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.shizhongcai.business.alipaydemo.domain.vo.AesTestReqVo;
import com.shizhongcai.common.utils.EncryptUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DreamStartTests {

	@Value("${demo.aes.key}")
	private String key;

	@Resource
	private DefaultAlipayClient defaultAlipayClient;
	@Test
	public void testAes() throws Exception{

		AesTestReqVo reqVo = new AesTestReqVo();
		reqVo.setName("name");
		System.out.println("加密数据:"+EncryptUtils.encryptByAES(JSON.toJSONString(reqVo),key));
	}

	/**
	 * 扫码支付测试
	 */
	@Test
	public void testAliPay(){
		AlipayTradePrecreateRequest preCreateRequest = new AlipayTradePrecreateRequest();
		AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
		model.setSubject("测试");
		model.setTotalAmount("8.88");
		model.setStoreId("");
		model.setTimeoutExpress("5m");
		model.setOutTradeNo("20191231142101001");
		preCreateRequest.setBizModel(model);
		preCreateRequest.setNotifyUrl("http://1b51z57431.51mypc.cn/notify");
		try {
			AlipayTradePrecreateResponse preCreateResponse = defaultAlipayClient.certificateExecute(preCreateRequest);
			if(preCreateResponse.isSuccess()){
				System.out.println("调用成功");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}


}
