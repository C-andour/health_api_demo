package com.itrihua.health_api_demo.utils;

import com.itrihua.health_api_demo.Exception.BaseException;
import com.itrihua.health_api_demo.enums.AppHttpCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class WechatUtil {
    @Value("${wx.AppID}")
    private String APPID;
    @Value("${wx.AppSecret}")
    private String SECRET;
    @Value("${wx.JS_URL}")
    private String JS_URL;
    private String JS_CODE; //前端传递

    private final Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    /**
     * 小程序登录,发起https请求,
     *      获取 用户信息
     * @param code 单次校验码
     * @return UserInfo {openid , session_key}
     */
    public String getUserInfo(String code) throws BaseException{
        // 1 获取code,拼接url,发起请求,获取UserInfo
        JS_CODE = code;
        String url = JS_URL +
                "?appid=" + APPID +
                "&secret=" + SECRET +
                "&js_code=" + JS_CODE +
                "&grant_type=authorization_code";
        String UserInfo = null;
        try {
            // 2 发起请求
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            //返回码 200 ?
            //TODO: 异常类型添加
            int responseCode = con.getResponseCode();
            if (responseCode != 200){
                throw new BaseException(AppHttpCodeEnum.BAD_GATEWAY);
            }
            logger.info("responseCode:"+ responseCode);
            // 3 读取返回结果
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info("UserInfo:" + response.toString());
            // 4 获取UserInfo
            UserInfo = response.toString();

        }catch (Exception e){
            throw new BaseException(AppHttpCodeEnum.SERVER_ERROR);
        }

        return UserInfo;
    }
}
