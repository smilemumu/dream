package com.shizhongcai.business.common.exception;

import com.shizhongcai.business.common.domain.enums.ErrorCodesEnum;
import lombok.Data;

@Data
public class BaseException extends Exception{

    private int code;
    private String msg;


    public BaseException(ErrorCodesEnum errorCode){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }
}
