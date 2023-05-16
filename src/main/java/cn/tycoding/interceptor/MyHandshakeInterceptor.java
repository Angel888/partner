package cn.tycoding.interceptor;

import cn.tycoding.dto.ResponseInfo;
import cn.tycoding.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
// 参考 https://blog.csdn.net/July_whj/article/details/108947207
@Slf4j
@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {
    /**
     * 握手之前，若返回false，则不建立链接 *
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse
            response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        //将用户id放入socket处理器的会话(WebSocketSession)中
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        //获取参数
        String tokenId = serverHttpRequest.getServletRequest().getParameter("token");
//        attributes.put("uid", userId);
        //可以在此处进行权限验证，当用户权限验证通过后，进行握手成功操作，验证失败返回false
        String tokenRaw=JWTUtil.validateToken(tokenId);
        if (tokenRaw==null) {
            log.error("握手失败.....");
            return false;
        }
        System.out.println("开始握手。。。。。。。");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse
            response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("握手成功啦。。。。。。");
    }
}
