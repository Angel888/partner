import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;

public class JwtTokenTest {
    /**
     * 令牌的获取
     * 测试生成jwt的token令牌三部分组成
     * header(加密算法,类型) . payload(负载,可以不重要可以用得到的用户信息) . signature(签名,前两部跟base64加密和自己的秘钥)
     */
    @Test
    public void jwtToken() {
        HashMap<String, Object> map = new HashMap<>();//header中需要传递的map,无内容时使用jwt默认值
        //设置一个时间,作为令牌的过期时间 ,设置过期时间为60秒
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 60);

        String token = JWT.create().withHeader(map)//header中已经有默认的算法和类型
                .withClaim("userId", 123456) //payload负载
                .withClaim("userName", "zhangsan") //payload可以传递多个
                .withExpiresAt(calendar.getTime())  //令牌不是无限期的,设置令牌的过期时间
                .sign(Algorithm.HMAC256("miyao"));//签名设计加密算法,还有设置自己的秘钥

        System.out.println(token);
    }

    /**
     * 解析token,注意有效期内
     */
    @Test
    public void verifyTest() {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("miyao")).build();
        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6InpoYW5nc2FuIiwiZXhwIjoxNjIyMTIwNzgzLCJ1c2VySWQiOjEyMzQ1Nn0.wCobBUz5oRVmeaTBT5Omo_Ab3iTid8JrXRfqmxC-wqs");
        //解析完成之后可以获取payload负载中自包含放置的信息
        System.out.println(verify.getClaims());
        System.out.println(verify.getClaim("userId").asInt());
        System.out.println(verify.getClaim("userName").asString());
        //获取过期时间
        System.out.println(verify.getExpiresAt());

    }
}
