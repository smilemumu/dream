package com.shizhongcai.common.exception;

public class BaseException extends Exception{

    private int code;

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
