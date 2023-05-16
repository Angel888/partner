package cn.tycoding.util;


import cn.tycoding.dto.ResponseCodeEnums;
import cn.tycoding.dto.ResponseInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;


/**
 * @Author: xiang
 * @Date: 2021/5/11 21:11
 * <p>
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）,默认：{"alg": "HS512","typ": "JWT"}
 * payload的格式 设置：（用户信息、创建时间、生成时间）
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */
@Slf4j
@Component
@Data
@ConfigurationProperties(prefix = "jwt-config.yml")
public class JWTUtil {

    // 定义token返回头部
    public static String header;
    public static final String SIGNATURE = "miyao";
    //token前缀
    public static String tokenPrefix;

    //签名密钥
    public static String secret;

    //有效期
    public static long expireTime;

    //存进客户端的token的key名
    public static final String USER_LOGIN_TOKEN = "USER_LOGIN_TOKEN";

    public void setHeader(String header) {
        JWTUtil.header = header;
    }

    public void setTokenPrefix(String tokenPrefix) {
        JWTUtil.tokenPrefix = tokenPrefix;
    }


    public void setSecret(String secret) {
        JWTUtil.secret = secret;
    }

    public void setExpireTime(int expireTimeInt) {
        JWTUtil.expireTime = expireTimeInt * 1000L * 60;
    }

    /**
     * 创建TOKEN
     * @param sub
     * @return
     */
    //这个逻辑可能有用 先不删
//    public static String createToken(String sub){
//        return tokenPrefix + JWT.create()
//                .withSubject(sub)
//                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
//                .sign(Algorithm.HMAC512(secret));
//    }


//    原文链接：https://blog.csdn.net/weixin_46649054/article/details/117340735

    /**
     * 验证token
     *
     * @param token
     */
    public static String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getSubject();
        } catch (TokenExpiredException e) {
            log.error("token已经过期");
            return null;
        } catch (Exception e) {
            log.error("token验证失败");
            return null;
        }
    }

    /**
     * 检查token是否需要更新
     * @param token
     * @return
     */
    //这个逻辑可能有用 先不删
//    public static boolean isNeedUpdate(String token){
//        //获取token过期时间
//        Date expiresAt = null;
//        try {
//            expiresAt = JWT.require(Algorithm.HMAC512(secret))
//                    .build()
//                    .verify(token.replace(tokenPrefix, ""))
//                    .getExpiresAt();
//        } catch (TokenExpiredException e){
//            return true;
//        } catch (Exception e){
//            throw new ApiException(ResultInfo.unauthorized("token验证失败"));
//        }
//        //如果剩余过期时间少于过期时常的一般时 需要更新
//        return (expiresAt.getTime()-System.currentTimeMillis()) < (expireTime>>1);
//    }
}