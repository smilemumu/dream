package com.shizhongcai.common.enums;

public enum ErrorCodesEnum {

    SUCCESS(10000,"成功"),
    DUPLICATE_KEY(10001,"数据库中已存在该记录"),
    NO_HANDLER_FOUND(10002,"路径不存在，请检查路径是否正确"),
    DEFAULT_FAIL(10003,"未知异常，请稍后再试"),
    DECRYPT_ERROR(10004,"解密出错"),
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
