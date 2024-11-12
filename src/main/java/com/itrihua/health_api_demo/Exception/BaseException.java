package com.itrihua.health_api_demo.Exception;

import com.itrihua.health_api_demo.enums.AppHttpCodeEnum;

/**
 * 基础异常类
 */
public class BaseException extends RuntimeException{

    private AppHttpCodeEnum appHttpCodeEnum;

    public AppHttpCodeEnum getAppHttpCodeEnum(){
        return this.appHttpCodeEnum;
    }
    public BaseException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

}
