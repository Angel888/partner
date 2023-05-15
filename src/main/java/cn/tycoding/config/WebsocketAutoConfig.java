package cn.tycoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author tycoding
 * @date 2019-06-10
 */
@Configuration
public class WebsocketAutoConfig {  //todo 没看出来这个类有啥用？

    @Bean
    public ServerEndpointExporter endpointExporter() {
        return new ServerEndpointExporter();
    }
}
