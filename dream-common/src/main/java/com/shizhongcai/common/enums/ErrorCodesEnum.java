package com.shizhongcai.common.enums;

public enum ErrorCodesEnum {

    SUCCESS(10000,"成功"),
    DUPLICATE_KEY(10001,"数据库中已存在该记录"),
    NOHANDLER_FOUND(10002,"路径不存在，请检查路径是否正确"),
    DEFAULT_FAIL(10000,"未知异常，请稍后再试"),
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
