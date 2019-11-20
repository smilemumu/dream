package com.shizhongcai.business.common.exception;

import com.shizhongcai.business.common.domain.vo.BaseRspVo;
import com.shizhongcai.business.common.domain.enums.ErrorCodesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public BaseRspVo handleTSharkException(BaseException e) {
        logger.error("{}",e);
        return BaseRspVo.fail(e);
    }

    /**
     * 未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public BaseRspVo handleException(Exception e) {
        logger.error("{}",e);
        return BaseRspVo.fail(ErrorCodesEnum.DEFAULT_FAIL);
    }

    /**
     * 参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseRspVo handleException(MethodArgumentNotValidException e) {
        logger.error("请求参数不合法{}",e.getBindingResult());
        //获取所有校验失败项
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        //处理异常校验项
        Map<String,String> errorMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(errors)) {
            for (FieldError error : errors) {
                errorMap.put(error.getField(),error.getDefaultMessage());
            }
        }
        BaseRspVo baseRspVo =  BaseRspVo.fail(ErrorCodesEnum.PARAM_ERROR);
        baseRspVo.setData(errorMap);
        return baseRspVo;
    }


    /**
     * 路径不存在
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseRspVo handlerNoFoundException(Exception e) {
        logger.error("{}",e);
        return BaseRspVo.fail(ErrorCodesEnum.NO_HANDLER_FOUND);
    }



}
