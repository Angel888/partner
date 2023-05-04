//package cn.tycoding.util;
//import cn.tycoding.entity.UserMapper;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//
//import cn.tycoding.entity.User;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
//@Slf4j
//@Component
//public class TokenUtils {
//
//    @Resource
//    private UserMapper userMapper;
//
//    private static UserMapper staticUserMapper;
//
//    @PostConstruct
//    public void init() {
//        staticUserMapper = userMapper;
//    }
//
//    /**
//     * 生成token
//     *
//     * @param user
//     * @return
//     */
//    public static String createToken(UserMapper userId) {
//        return JWT.create().withExpiresAt(DateUtil.offsetDay(new Date(), 1))
//                .withAudience(userId.toString())
//                .sign(Algorithm.HMAC256("User.getPassword(userId)"));
//    }
//
//    /**
//     * 获取token中的用户信息
//     *
//     * @return
//     */
//    public static User getUser() {
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        try {
//            String token = request.getHeader("token");
//            String aud = JWT.decode(token).getAudience().get(0);
//            Integer userId = Integer.valueOf(aud);
//            User user = staticUserMapper.queryuserbyid(userId);//获取user信息
////            createToken(userId);//设置token
//            return user;
//        } catch (Exception e) {
//            log.error("解析token失败", e);
//            return null;
//        }
//
//    }
//
//    /**
//     * 获取token中的用户信息
//     *
//     * @return
//     */
//    public static User getUser(String token) {
//        try {
//            String aud = JWT.decode(token).getAudience().get(0);
//            Integer userId = Integer.valueOf(aud);
//            User user = staticUserMapper.queryuserbyid(userId);//获取user信息
//            user.setToken(token);//设置token
//            return user;
//        } catch (Exception e) {
//            log.error("解析token失败", e);
//            return null;
//        }
//
//    }
//
