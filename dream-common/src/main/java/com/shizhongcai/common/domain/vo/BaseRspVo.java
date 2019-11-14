package com.shizhongcai.common.domain.vo;

import com.shizhongcai.common.enums.ErrorCodesEnum;
import lombok.Data;

@Data
public class BaseRspVo<T> {
    private boolean success;
    private String msg;
    private Integer errorCode;
    private T data;


    public BaseRspVo (T data){
        this.success = true;
        this.msg= ErrorCodesEnum.SUCCESS.getMsg();
        this.errorCode = ErrorCodesEnum.SUCCESS.getCode();
        this.data = data;
    }
    public BaseRspVo (boolean success, String msg, int errorCode) {
        this.success = success;
        this.msg = msg;
        this.errorCode = errorCode;
    }

    public BaseRspVo (boolean success, String msg, int errorCode, T data) {
        this(success,msg,errorCode);
        this.data = data;
    }


    public BaseRspVo(ErrorCodesEnum errorCode) {
        if(errorCode == ErrorCodesEnum.SUCCESS) {
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
    public static BaseRspVo fail(ErrorCodesEnum errorCode){
        return new BaseRspVo(errorCode);
    }

    public static BaseRspVo fail(String msg){
        return new BaseRspVo(false,msg, ErrorCodesEnum.DEFAULT_FAIL.getCode());
    }





}
