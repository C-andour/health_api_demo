package com.itrihua.health_api_demo.filters;

import com.itrihua.health_api_demo.constants.AuthConstants;
import com.itrihua.health_api_demo.context.UserContext;
import com.itrihua.health_api_demo.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private final JWTUtil jwtUtil;
    private final UserContext userContext;

    public AuthInterceptor(JWTUtil jwtUtil, UserContext userContext) {
        this.jwtUtil = jwtUtil;
        this.userContext = userContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 获取用户信息, 获取request
        String path = request.getRequestURI();
        logger.info("path = " + path);

        // 2. 判断是否需要做登录校验, 根据AuthProperties的路由
        if (isExclude(path)) {
            return true;
        }
        //TODO: 测试用,全部放行
//        if(test()){
//            return true;
//        }

        // 3. 获取token
        String token = request.getHeader(AuthConstants.TOKEN);
        logger.info("token : " + token);

        // 4. 解析并验证token
        String openid = jwtUtil.parseToken(token);
        logger.info("openid : " + openid);

        // 5. 传递用户信息
        userContext.setCurrentId(openid);

        // 6. 放行
        return true;
    }


    /**
     * 放行路由校验
     *      此处为登录时放行
     */
    public boolean isExclude(String path){
        if (path.startsWith("/login/login")) {
            return true;
        }
        return false;
    }

    /**
     * 用于测试
     * @return
     */
    public boolean test(){
        return true;
    }
}
