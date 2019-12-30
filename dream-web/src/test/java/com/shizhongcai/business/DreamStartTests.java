package com.shizhongcai.business;

import com.alibaba.fastjson.JSON;
import com.shizhongcai.business.demo.domain.vo.AesTestReqVo;
import com.shizhongcai.common.utils.EncryptUtils;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DreamStartTests {

	@Value("${demo.aes.key}")
	private String key;
	@Test
	public void testAes() throws Exception{

		AesTestReqVo reqVo = new AesTestReqVo();
		reqVo.setName("name");
		System.out.println("加密数据:"+EncryptUtils.encryptByAES(JSON.toJSONString(reqVo),key));
	}

}
