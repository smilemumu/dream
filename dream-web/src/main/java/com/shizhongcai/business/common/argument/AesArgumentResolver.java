package com.shizhongcai.business.common.argument;

import com.alibaba.fastjson.JSON;
import com.shizhongcai.business.common.annotation.AesJson;
import com.shizhongcai.business.common.domain.data.AesBaseParams;
import com.shizhongcai.business.common.domain.enums.ErrorCodesEnum;
import com.shizhongcai.business.common.exception.BaseException;
import com.shizhongcai.common.utils.EncryptUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @Author shizhongcai
 * @Date 2019/11/15 10:51
 */
public class AesArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(AesArgumentResolver.class);

    @Value("${demo.aes.key}")
    private String key;
    /**
     * 支持该注解的方法
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(AesJson.class);
    }

    /**
     * 解析aes加密入参
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String para = getAllParamJson(httpServletRequest);
        AesBaseParams aesBaseParams = JSON.parseObject(para, AesBaseParams.class);
        Object obj = null;
        if(Objects.nonNull(aesBaseParams)){
            //如果是aes加密的请求参数不为空
            String data  = aesBaseParams.getData();
            if(StringUtils.isNotEmpty(data)){
                //如果不为空 ，进行解密,data为加密数据，key为aes密钥，此工具类自行获取
                String aesDecodeStr = EncryptUtils.decryptByAES(data,key);
                //将解密后的json字符串，解析成实体对象
                obj = JSON.parseObject(aesDecodeStr,methodParameter.getParameterType());
            }
        }
        if(Objects.isNull(obj)){
            //抛出异常，解密出错
            throw new BaseException(ErrorCodesEnum.DECRYPT_ERROR);
        }
        return obj;
    }

    /**
     *
     * 获取application/json格式提交的请求参数的 json字符串
     * @param httpServletRequest
     * @return
     * @throws IOException
     */
    private String getAllParamJson(HttpServletRequest httpServletRequest) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = httpServletRequest.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        return stringBuilder.toString();
    }
}
