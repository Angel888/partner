package cn.tycoding.controller;

import cn.tycoding.service.ChatSessionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author tycoding
 * @date 2019-06-10
 */
@Slf4j // 日志
@Component
@ServerEndpoint(value = "/chat/sys/{conversation_id}")
public class WebsocketServerEndpointSys {
    private static CopyOnWriteArraySet<WebsocketServerEndpointSys> websocketServerEndpointSys = new CopyOnWriteArraySet<>(); // 不群发消息，这个arraySet先忽略吧


    private static ChatSessionService chatSessionService;  //todo  到底是Private还是Public?到底是final还是static?
    @Autowired
    public void setChatSessionService(ChatSessionService chatSessionService) { // todo 为什么用Setter方法注入，而不是构造器注入
        WebsocketServerEndpointSys.chatSessionService = chatSessionService;
    }
//    log.info() // todo 既然是先装配再构造为什么不能使用log.info()

    // 这段构造器注入有bug  //todo 回头去掉注释再研究一下
//    {
//    private final StringRedisTemplate redisTemplate;
//
//    @Autowired
//    public WebsocketServerEndpointSys(StringRedisTemplate redisTemplate, ChatSessionService chatSessionService) {
//        this.redisTemplate = redisTemplate;
////        this.chatSessionService = chatSessionService;
//    }
//    }

    //在线连接数
    private static long online = 0;


    /**
     * 链接成功调用的方法
     *
     * @param conversation_id
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("conversation_id") String conversation_id) throws IOException {
        log.info("onOpen >> 链接成功");

        websocketServerEndpointSys.add(this);

        chatSessionService.OnOpenSys(conversation_id, session);
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        log.info("onClose >> 链接关闭");
        chatSessionService.OnCloseSys();
        //移除当前Websocket对象
        websocketServerEndpointSys.remove(this);

        //在内线人数-1
        subOnLineCount();

        log.info("链接关闭，当前在线人数：" + getOnlineCount());
    }

    /**
     * 收到用户消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message, @PathParam("conversation_id") String conversation_id) throws IOException {
        // 用户的返回符合条件 就存 redis  继续问下面的
        // 不符合条件，就再问一遍 先一律存下来，默认的提问模板使用map key代表第几条  我怎么知道接下来要问第几个了
        log.info("接收到窗口：" + conversation_id + " 的信息：" + message);
        chatSessionService.OnMessageSys( conversation_id , message);

        //群发消息
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }



    /**
     * 群发消息
     *
     * @param data
     */
    private void sendMore(String data) {
        for (WebsocketServerEndpointSys websocketServerEndpointSys : WebsocketServerEndpointSys.websocketServerEndpointSys) {
            try {
                chatSessionService.SendMessage(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void subOnLineCount() {
        WebsocketServerEndpointSys.online--;
    }

    private synchronized long getOnlineCount() {
        return online;
    }

    private void addOnlineCount() {
        WebsocketServerEndpointSys.online++;
    }
}
