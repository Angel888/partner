package cn.tycoding.controller;

import cn.tycoding.service.ChatSessionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
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

    // 这段构造器注入有bug
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

    //用于存放当前Websocket对象的Set集合

    //与客户端的会话Session


    //当前会话窗口ID  用户和系统交互时，会话id即用户id，当会话结束后，信息会被清除





    /**
     * 链接成功调用的方法
     *
     * @param conversation_id
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("conversation_id") String conversation_id) {
        log.info("onOpen >> 链接成功");
        //从token获取用户数据
//        User tokenUser = TokenUtils.getUser(token);
//        Integer userId = Math.toIntExact(tokenUser.getId());

        websocketServerEndpointSys.add(this);

        chatSessionService.OnOpenSys(conversation_id,session);

        // 暂时不需要获取用户信息
//        try {
//            User user = chatSessionService.findById(fromId);
//            //群发消息
//            Map<String, Object> map = new HashMap<>();
//            map.put("msg", "用户 " + user.getName() + " 已上线");
//            sendMore(JSONObject.toJSONString(map));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("onClose >> 链接关闭");

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
     * 封装返回消息
     *
     * @param toId    指定窗口ID
     * @param message 消息内容
     * @return
     * @throws IOException
     */
//    private String getData(String toId, String message) throws IOException {
//        Message entity = new Message();
//        entity.setMessage(message);
//        entity.setTime(CoreUtil.format(new Date()));
//        entity.setFrom(chatSessionService.findById(conversation_id));
//        entity.setTo(chatSessionService.findById(toId));
//        return JSONObject.toJSONString(new R(entity));
//    }

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

    /**
     * 指定窗口推送消息
     *
//     * @param entity 推送消息
//     * @param toId   接收方ID
     */
    //todo
//    public void sendTo(String toId, Message entity) {
//        fromId = entity.getFrom().getId().toString();
//        if (websocketServerEndpointSys.size() <= 1) {
//            throw new GlobalException("用户未上线");
//        }
//        boolean flag = false;
//        for (WebsocketServerEndpointSys endpoint : websocketServerEndpointSys) {
//            try {
//                if (endpoint.fromId.equals(toId)) {
//                    flag = true;
//                    log.info(entity.getFrom().getId() + " 推送消息到窗口：" + toId + " ，推送内容：" + entity.getMessage());
//
//                    chatSessionService.SendMessage(getData(toId, entity.getMessage()));
//                    chatSessionService.pushMessage(fromId, toId, entity.getMessage());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                continue;
//            }
//        }
//        if (!flag) {
//            throw new GlobalException("推送失败，找不到该窗口");
//        }
//    }

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
