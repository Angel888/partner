package cn.tycoding.config;

import cn.tycoding.controller.WebsocketServerEndpointSys;
import cn.tycoding.dto.MyWebSocketHandler;
import cn.tycoding.interceptor.MyHandshakeInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    /**
     * 注入拦截器
     */
    @Resource
    private MyHandshakeInterceptor myHandshakeInterceptor;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry
                //添加myHandler消息处理对象，和websocket访问地址
                .addHandler(MyWebSocketHandler(), "/*")
                //设置允许跨域访问
                .setAllowedOrigins("*")
                //添加拦截器可实现用户链接前进行权限校验等操作
                .addInterceptors(myHandshakeInterceptor);
    }

    @Bean
    public WebSocketHandler MyWebSocketHandler() {
        return new MyWebSocketHandler();
    }
}
