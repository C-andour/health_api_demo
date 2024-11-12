package com.itrihua.health_api_demo.Exception;

import com.itrihua.health_api_demo.enums.AppHttpCodeEnum;

/**
 * token校验异常类
 */
public class AuthException extends RuntimeException{

    private AppHttpCodeEnum appHttpCodeEnum;

    public AuthException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public AppHttpCodeEnum getAppHttpCodeEnum(){
        return this.appHttpCodeEnum;
    }

}
