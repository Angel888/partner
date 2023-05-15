package cn.tycoding.util;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Socket 拦截器
 *
 * @author Strive
 * @date 2022/4/21 17:10
 * @description
 */
//todo 看看拦截器怎么写？
//@Slf4j
//@Component
//public class MingYueSocketInterceptor implements HandshakeInterceptor {
//    @Override
//    public boolean beforeHandshake(
//            ServerHttpRequest request,
//            ServerHttpResponse response,
//            WebSocketHandler wsHandler,
//            Map<String, Object> attributes)
//            throws Exception {
//        log.info("握手开始");
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(
//            ServerHttpRequest request,
//            ServerHttpResponse response,
//            WebSocketHandler wsHandler,
//            Exception exception) {
//        log.info("握手完成");
//    }
//}

