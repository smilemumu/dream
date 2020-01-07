package com.shizhongcai.business.alipaydemo.controller;

import com.alipay.api.internal.util.file.IOUtils;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.shizhongcai.business.common.domain.vo.BaseRspVo;
import com.shizhongcai.business.common.utils.Request2Map;

import com.shizhongcai.business.alipaydemo.domain.vo.AliScanPayCancelTradeReqVo;
import com.shizhongcai.business.alipaydemo.domain.vo.AliScanPayTradeQueryReqVo;
import com.shizhongcai.business.alipaydemo.service.AliPayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author shizhongcai
 * @Date 2019/12/31 15:30
 */
@RestController
@RequestMapping("/pay/ali")
public class AliPayController {

    @Resource
    private AliPayService aliPayService;

    /**
     * 获取支付宝支付扫码二维码
     * @return
     */
    @GetMapping(value = "/getQrCode")
    public void getQrCode(@RequestParam String outTradeNo, HttpServletResponse response){
        String qrCode =aliPayService.getQrCode(outTradeNo);
        Map<EncodeHintType, Object> hints = new HashMap(3);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 3);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            BitMatrix bitMatrix = (new MultiFormatWriter()).encode(qrCode, BarcodeFormat.QR_CODE, 200, 200, hints);
            MatrixToImageConfig config = new MatrixToImageConfig(-16777215, -1);
            BufferedImage bufImg = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bufImg,"png",out);
            IOUtils.closeQuietly(out);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 支付宝二维码扫码后付款结果回调
     * @return
     */
    @PostMapping(value = "/scan_pay/callback")
    public String scanPayCallBack(HttpServletRequest request){
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = Request2Map.toMap(request);
        return aliPayService.scanCallBack(params);
    }

    /**
     * 扫码付轮询支付结果，超过轮询时间后未支付则取消订单
     */
    @PostMapping(value = "/loopTradeQuery")
    public BaseRspVo loopTradeQuery(@RequestBody AliScanPayTradeQueryReqVo reqVo){
        AlipayTradeQueryResponse response = aliPayService.loopTradeQuery(reqVo);
        return BaseRspVo.success(response);
    }

    /**
     * 扫码付超过轮询时间后未支付则取消订单
     */
    @PostMapping(value = "/cancelTrade")
    public BaseRspVo cancelTrade(@RequestBody AliScanPayCancelTradeReqVo reqVo){
        AlipayTradeCancelResponse response = aliPayService.cancelTrade(reqVo);
        return BaseRspVo.success(response);
    }

}
