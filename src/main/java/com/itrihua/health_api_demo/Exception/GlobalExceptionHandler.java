package com.itrihua.health_api_demo.Exception;

import com.itrihua.health_api_demo.model.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常处理器,接受所有的BaseException
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseResult exceptionHandler(BaseException ex) {
        return ResponseResult.errorResult(ex.getAppHttpCodeEnum());
    }

    /**
     * token校验异常类,接受token校验
     */
    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public ResponseResult authenticationHandler(AuthException ex) {
        return ResponseResult.errorResult(ex.getAppHttpCodeEnum());
    }


}
