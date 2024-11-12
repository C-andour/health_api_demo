package com.itrihua.health_api_demo.controller;

import com.itrihua.health_api_demo.model.ResponseResult;
import com.itrihua.health_api_demo.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/getAT/{code}")
    public String get_access_Token(@PathVariable("code") String code){
        logger.info("code:" + code);
        String accessToken = loginService.getAccessToken(code);
        return accessToken;
    }

    @PostMapping("/login/{code}")
    public ResponseResult Login(@PathVariable("code") String code) {
        logger.info("登录校验,单次校验码:" + code);
        //登录后返回token,每次登录携带token,进行校验

        String token = loginService.Login(code);
        return ResponseResult.okResult(token);

    }



}
