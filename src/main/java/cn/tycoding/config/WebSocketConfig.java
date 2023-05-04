//package cn.tycoding.config;
//
//import cn.tycoding.security.JwtTokenProvider;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptorAdapter;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//
//@Configuration
//@EnableWebSocketMessageBroker
//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
//public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
//
//    private final static Logger logger = LogManager.getLogger(WebSocketConfig.class.getName());
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
////    @Autowired
////    private PrivateChatService privateChatService;
//
//    private static final String MESSAGE_PREFIX = "/topic";
//    private static final String END_POINT = "/chat";
//    private static final String APPLICATION_DESTINATION_PREFIX = "/live";
//
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        if (registry != null) {
//            registry.addEndpoint(END_POINT).setAllowedOrigins("*").withSockJS();
//        }
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        if (registry != null) {
//            registry.enableSimpleBroker(MESSAGE_PREFIX);
//            registry.setApplicationDestinationPrefixes(APPLICATION_DESTINATION_PREFIX);
//        }
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.setInterceptors(new ChannelInterceptorAdapter() {
//
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                    String authToken = accessor.getFirstNativeHeader("Authentication");
//                    String jwt = JwtUtils.resolveToken(authToken);
//                    if (jwtTokenProvider.validateToken(jwt)) {
//                        Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
//                        accessor.setUser(authentication);
//                        String itemId = accessor.getFirstNativeHeader("item_id");
////                        accessor.setDestination("/topic" + privateChatService.getChannelId(itemId, authentication.getName()));
//                        logger.info(accessor.getDestination()); //ex: /topic/chat/3434/chat_with/user3797474342423
//                    }
//                }
//                return message;
//            }
//        });
//    }
//}
