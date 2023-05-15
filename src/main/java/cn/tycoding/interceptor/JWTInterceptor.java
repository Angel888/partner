//package cn.tycoding.interceptor;
//
//import cn.tycoding.util.JWTUtils;
//import com.auth0.jwt.exceptions.AlgorithmMismatchException;
//import com.auth0.jwt.exceptions.InvalidClaimException;
//import com.auth0.jwt.exceptions.SignatureVerificationException;
//import com.auth0.jwt.exceptions.TokenExpiredException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//
//public class JWTInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HashMap<String, Object> map = new HashMap<>();
//        String token = request.getHeader("token");  //从request中获取到请求头中的token,进行解析校验
//        try {
//            JWTUtils.verifyToken(token);//调用token解析的工具类进行解析
//            return true;  //请求放行
//        } catch (SignatureVerificationException e) {
//            e.printStackTrace();
//            map.put("msg", "签名不一致异常");
//        } catch (TokenExpiredException e) {
//            e.printStackTrace();
//            map.put("msg", "令牌过期异常");
//        } catch (AlgorithmMismatchException e) {
//            e.printStackTrace();
//            map.put("msg", "算法不匹配异常");
//        } catch (InvalidClaimException e) {
//            e.printStackTrace();
//            map.put("msg", "失效的payload异常");
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("msg", "token无效");
//        }
//        //map异常的数据要返回给客户端需要转换成json格式  @ResponseBody 内置了jackson
//        String resultJson = new ObjectMapper().writeValueAsString(map);
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().print(resultJson);
//        return false;  //异常不放行
//    }
//}
//        //原文链接：https://blog.csdn.net/weixin_46649054/article/details/117340735
