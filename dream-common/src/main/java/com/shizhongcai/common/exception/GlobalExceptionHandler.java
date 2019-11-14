package com.shizhongcai.common.exception;

import com.shizhongcai.common.domain.vo.BaseRspVo;
import com.shizhongcai.common.enums.ErrorCodesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public BaseRspVo handleTSharkException(BaseException e) {
        logger.error(e.getMessage());
        return BaseRspVo.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseRspVo handleException(Exception e) {
        logger.error(e.getMessage());
        return BaseRspVo.fail(e.getMessage());
    }

    /**
     * 路径不存在
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseRspVo handlerNoFoundException(Exception e) {
        logger.error(e.getMessage());
        return BaseRspVo.fail(ErrorCodesEnum.DUPLICATE_KEY);
    }

    /**
     * 重复主键
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public BaseRspVo handleDuplicateKeyException(DuplicateKeyException e){
        logger.error(e.getMessage());
        return BaseRspVo.fail(ErrorCodesEnum.NOHANDLER_FOUND);
    }

}
