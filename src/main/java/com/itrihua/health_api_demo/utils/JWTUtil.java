package com.itrihua.health_api_demo.utils;

import com.itrihua.health_api_demo.Exception.AuthException;
import com.itrihua.health_api_demo.constants.AuthConstants;
import com.itrihua.health_api_demo.enums.AppHttpCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现jwt校验,加密
 */
@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.TOKEN_EXPIRATION_TIME}")
    private Long TOKEN_EXPIRATION_TIME;
    @Value("${jwt.secretString}")
    private String secretString;

    private Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * 生成TOKEN
     * @param openid    唯一标识
     * @return  TOKEN   伴随传输头进行传输
     */
    public String getToken(String openid){
        //1.创建claims,放入openid
        Map<String, Object> cliams = new HashMap<>();
        cliams.put(AuthConstants.OPEN_ID, openid);

        //2.设定过期时间
        //获取当前时间戳
        //累加设定时间
        long currentTime = System.currentTimeMillis();
        Date expirationDate = new Date(currentTime + TOKEN_EXPIRATION_TIME * 1000);

        //3.生成secretKey
        SecretKey key = getSecretKey(secretString);

        //3.使用jwtbuilder,生成token
        String token;
        token = Jwts.builder()
                .header()
                .add("typ", AuthConstants.JWT_HEADER_TYP)
                .add("alg", AuthConstants.HS256)
                .and()
                .claims(cliams)
                .expiration(expirationDate)
                .signWith(key)
                .compact();

        return token;
    }

    /**
     * 解析Token,成功则返回openid
     *  出现问题,抛出异常
     * @return openid 用户唯一标识
     */
    public String parseToken(String token) throws AuthException {
        String openid = null;

        //1.检查token是否为null
        if(token == null){
            logger.info("token is null");
            throw new AuthException(AppHttpCodeEnum.TOKEN_REQUIRE);
        }

        try {
            // 2. 解析token,检查头部签名算法
            if (!getAlgorithm(token).equals(AuthConstants.HS256)) {
                logger.info("Signature algorithm not supported");
                throw new AuthException(AppHttpCodeEnum.TOKEN_INVALID);
            }

            // 3. 解析获取Expiration,判断是否超时(jjwt已经提供实现,捕获即可,下面已经捕获)
            // 4. 解析获取openid
            openid = getOpenid(token);
            if (openid == null) {
                logger.info("The token does not contain an 'openid' claim. Ensure your token includes the required 'openid' claim.");
                throw new AuthException(AppHttpCodeEnum.TOKEN_INVALID);
            }
        }catch (ExpiredJwtException e){
            // 重新抛出自己的时间过期异常(3)
            logger.info("Expired JWT token");
            throw new AuthException(AppHttpCodeEnum.TOKEN_EXPIRE);
        }
        catch (Exception e) {
            // 捕获其他所有异常并抛出通用的解析异常
            logger.info("JWT Parse error,Invalid JWT");
            throw new AuthException(AppHttpCodeEnum.TOKEN_INVALID);
        }

        return openid;
    }

    /**
     * 解析token,获取jws的payload、header等内容
     * 解析主体函数
     */
    private Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey(secretString))
                .build()
                .parseSignedClaims(token);
    }

    /**
     * 获取jwt header
     */
    private JwsHeader getHeaders(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 获取头部中的签名算法
     */
    private String getAlgorithm(String token) {
        return (String) getHeaders(token).getAlgorithm();
    }

    /**
     * 获取jwt payload ,此处即是自定义的claims
     */
    private Claims getClaims(String token) {
        return getJws(token).getPayload();
    }

    /**
     * 获取openid
     */
    private String getOpenid(String token) {
        return (String) getClaims(token).get(AuthConstants.OPEN_ID);
    }

    /**
     * 获取sessionkey
     */
    private String getSessionKey(String token) {
        return (String) getClaims(token).get(AuthConstants.SESSION_KEY);
    }

    /**
     * 解析获取exp,判断是否过期
     */
    private boolean validateExp(String token) {
        //时间过期校验,其中new Date()获取当前时间,毫秒级别
        return getClaims(token).getExpiration().after(new Date());
    }


    /**
     * 根据secretString 编码成 secretKey
     * @param secretString 密码字符串
     * @return  密钥
     */
    private SecretKey getSecretKey(String secretString){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    }
}
