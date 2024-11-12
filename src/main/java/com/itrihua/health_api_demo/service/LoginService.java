package com.itrihua.health_api_demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.itrihua.health_api_demo.Exception.AuthException;
import com.itrihua.health_api_demo.Exception.BaseException;
import com.itrihua.health_api_demo.enums.AppHttpCodeEnum;
import com.itrihua.health_api_demo.mapper.UserMapper;
import com.itrihua.health_api_demo.model.ResponseResult;
import com.itrihua.health_api_demo.model.User;
import com.itrihua.health_api_demo.utils.JWTUtil;
import com.itrihua.health_api_demo.utils.StringUtils;
import com.itrihua.health_api_demo.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
public class LoginService {

    @Value("${wx.AppID}")
    private String APPID;
    @Value("${wx.AppSecret}")
    private String SECRET;
    private String JS_CODE;

    @Value("${candour.icon_url}")
    private String default_icon_url;
    @Value("${candour.name}")
    private String default_name;

    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final WechatUtil wechatUtil;
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;

    public LoginService(WechatUtil wechatUtil, UserMapper userMapper, JWTUtil jwtUtil) {
        this.wechatUtil = wechatUtil;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 获取at
     */
    public String getAccessToken(String code) {
        JS_CODE = code;
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + APPID +
                "&secret=" + SECRET +
                "&js_code=" + JS_CODE +
                "&grant_type=authorization_code";
        String accessToken = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            logger.info("responseCode:"+ responseCode);
            //读取内容
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info("Response:" + response.toString());
            accessToken = response.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return accessToken;
    }

    /**
     * 登录逻辑
     */
    public String Login(String code) throws BaseException{
        /**
         *
         * 1 根据code,向微信发起请求,查询openid
         * 2 根据openid,查询数据库是否存在
         *  2.1 若存在,不进行操作
         *  2.2 若不存在,则重新添加该用户
         * 3 返回openid生成的token
         */
        String userInfo = wechatUtil.getUserInfo(code);
        String openid = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HashMap<String,String> map = objectMapper.readValue(userInfo, HashMap.class);
            openid = map.get("openid");
            logger.info("openid: " + openid);
            if (StringUtils.isEmptyOrBlank(openid)){
                throw new AuthException(AppHttpCodeEnum.TOKEN_INVALID);
            }
        } catch (Exception e) {
            throw new AuthException(AppHttpCodeEnum.TOKEN_INVALID);
        }

        Long id = userMapper.getId(openid);
        if (id == null){
            logger.info("用户,openid:{},id:{}未存在数据库中",openid,id);
            // 2.2 添加用户
            User user = new User(openid,default_name,default_icon_url);
            userMapper.addUser(user);
        } else {
            // 2.1
            logger.info("用户,openid:{},id:{}已经存在数据库中",openid,id);
        }

        String token = jwtUtil.getToken(openid);

        return token;

    }
}
