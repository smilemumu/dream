package com.shizhongcai.common.exception;

import com.shizhongcai.common.enums.ErrorCodesEnum;
import lombok.Data;

@Data
public class BaseException extends Exception{

    private int code;
    private String msg;

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(ErrorCodesEnum errorCode){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }
}
