package com.shizhongcai.common.domain.vo;

import com.shizhongcai.common.enums.ErrorCodes;
import lombok.Data;

@Data
public class BaseRspVo<T> {
    private boolean success;
    private String msg;
    private Integer errorCode;
    private T data;


    public BaseRspVo (T data){
        this.success = true;
        this.msg=ErrorCodes.SUCCESS.getMsg();
        this.errorCode = ErrorCodes.SUCCESS.getCode();
        this.data = data;
    }
    public BaseRspVo (boolean success, String msg, int errorCode) {
        this.success = success;
        this.msg = msg;
        this.errorCode = errorCode;
    }

    public BaseRspVo (boolean success, String msg, int errorCode, T data) {
        this.success = success;
        this.msg = msg;
        this.errorCode = errorCode;
        this.data = data;
    }


    public BaseRspVo(ErrorCodes errorCode) {
        if(errorCode == ErrorCodes.SUCCESS) {
            this.success = true;
        } else {
            this.success = false;
        }
        this.msg = errorCode.getMsg();
        this.errorCode = errorCode.getCode();
    }

    public static BaseRspVo success(Object data){
        return new BaseRspVo(data);
    }
    public static BaseRspVo fail(ErrorCodes errorCode){
        return new BaseRspVo(errorCode);
    }

    public static BaseRspVo fail(String msg){
        return new BaseRspVo(false,msg,ErrorCodes.DEFAULT_FAIL.getCode());
    }





}
