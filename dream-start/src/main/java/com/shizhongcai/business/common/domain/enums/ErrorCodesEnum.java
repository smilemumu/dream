package com.shizhongcai.business.common.domain.enums;

public enum ErrorCodesEnum {

    SUCCESS(10000,"成功"),
    SYS_ERROR(1000,"系统异常，请稍后再试"),
    PARAM_ERROR(10001,"参数异常"),
    NO_HANDLER_FOUND(10002,"路径不存在，请检查路径是否正确"),
    DEFAULT_FAIL(10003,"未知异常，请稍后再试"),
    DECRYPT_ERROR(10004,"解密出错"),
    REQUEST_TOO_FAST(10005,"您的请求太快啦！请在稍后再试"),
    ;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误描述
     */
    private String msg;

    ErrorCodesEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
