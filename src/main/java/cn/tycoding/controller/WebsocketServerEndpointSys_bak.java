//package cn.tycoding.controller;
//
//
//import cn.tycoding.entity.Message;
//import cn.tycoding.exception.GlobalException;
//import cn.tycoding.service.ChatSessionService;
//import cn.tycoding.util.CoreUtil;
//import cn.tycoding.util.R;
//import com.alibaba.fastjson.JSONObject;
//import constant.CommonConstant;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.BoundValueOperations;
//import org.springframework.stereotype.Component;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.Date;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//import static constant.CommonConstant.Questions;
//// todo  服啦 别人的就可以 我的就不行  难道是因为@ServerEndpoint构造类的时候不允许传递属性？
///**
// * @author tycoding
// * @date 2019-06-10
// */
//@Slf4j // 日志
//@Component
////@RequiredArgsConstructor
//@ServerEndpoint(value = "/chat2/sys/{conversation_id}")
//public class WebsocketServerEndpointSys_bak {
//
//    private static ChatSessionService chatSessionService;
//
//    private final StringRedisTemplate redisTemplate;
//
//    //todo 1 构造器注入的方式是否可以使用@RequiredArgsConstructor 2 这一句会让StringRedisTemplate初始化吗？
//    @Autowired
//    public WebsocketServerEndpointSys_bak(StringRedisTemplate redisTemplate, ChatSessionService chatSessionService) {
//        this.redisTemplate = redisTemplate;
////        this.chatSessionService = chatSessionService;
//    }
//
//    //在线连接数
//    private static long online = 0;
//
//    //用于存放当前Websocket对象的Set集合
//    private static CopyOnWriteArraySet<WebsocketServerEndpointSys_bak> websocketServerEndpointSys = new CopyOnWriteArraySet<>(); // 不群发消息，这个arraySet先忽略吧
//
//    //与客户端的会话Session
//    private Session session;
//
//    //当前会话窗口ID  用户和系统交互时，会话id即用户id，当会话结束后，信息会被清除
//    private String fromId = "";
//    private Integer conversationNum = 0;
//
//
//
//    /**
//     * 链接成功调用的方法
//     *
//     * @param session
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("conversation_id") String conversation_id) {
//        log.info("onOpen >> 链接成功");
//        this.session = session;
//        this.conversationNum += 1;
//        //todo 写jwt校验
//        //将当前websocket对象存入到Set集合中
//        websocketServerEndpointSys.add(this);
//
//        //在线人数+1
//        addOnlineCount();
//
//        log.info("有新窗口开始监听：" + conversation_id + ", 当前在线人数为：" + getOnlineCount());
//
//        this.fromId = CommonConstant.USER_PREFIX + conversation_id;
//        redisTemplate.opsForHash().delete(this.fromId);
//        redisTemplate.opsForHash().put(this.fromId, 1, "连接成功，你好！");
//        try {
//            sendMessage("连接成功，你好！");
//        } catch (IOException e) {
//            log.error("websocket IO异常");
//        }
//        // 暂时不需要获取用户信息
////        try {
////            User user = chatSessionService.findById(fromId);
////            //群发消息
////            Map<String, Object> map = new HashMap<>();
////            map.put("msg", "用户 " + user.getName() + " 已上线");
////            sendMore(JSONObject.toJSONString(map));
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//    /**
//     * 链接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//        log.info("onClose >> 链接关闭");
//
//        //移除当前Websocket对象
//        websocketServerEndpointSys.remove(this);
//
//        //在内线人数-1
//        subOnLineCount();
//
//        log.info("链接关闭，当前在线人数：" + getOnlineCount());
//    }
//
//    /**
//     * 收到用户消息后调用的方法
//     *
//     * @param message
//     */
//    @OnMessage
//    public void onMessage(String message, @PathParam("conversation_id") String conversation_id) throws IOException {
//        // 用户的返回符合条件 就存 redis  继续问下面的
//        // 不符合条件，就再问一遍 先一律存下来，默认的提问模板使用map key代表第几条  我怎么知道接下来要问第几个了
//        log.info("接收到窗口：" + fromId + " 的信息：" + message);
//        this.conversationNum += 1;
//        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("fromId");
////        redisTemplate.opsForHash().put(fromId, boundValueOperations.size()+1,message);  // 这里不需要判断是否为Null了吧
//
//        chatSessionService.pushMessage(Questions[conversationNum], null, message);
//        sendMessage(message);
//        //群发消息
//    }
//
//    @OnError
//    public void onError(Throwable e) {
//        e.printStackTrace();
//    }
//
//    /**
//     * 推送消息
//     *
//     * @param message
//     */
//    // todo 是否可以放到一个websocket的service中
//    private void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//    /**
//     * 封装返回消息
//     *
//     * @param toId    指定窗口ID
//     * @param message 消息内容
//     * @return
//     * @throws IOException
//     */
//    private String getData(String toId, String message) throws IOException {
//        Message entity = new Message();
//        entity.setMessage(message);
//        entity.setTime(CoreUtil.format(new Date()));
//        entity.setFrom(chatSessionService.findById(fromId));
//        entity.setTo(chatSessionService.findById(toId));
//        return JSONObject.toJSONString(new R(entity));
//    }
//
//    /**
//     * 群发消息
//     *
//     * @param data
//     */
//    private void sendMore(String data) {
//        for (WebsocketServerEndpointSys_bak websocketServerEndpointSys : WebsocketServerEndpointSys_bak.websocketServerEndpointSys) {
//            try {
//                websocketServerEndpointSys.sendMessage(data);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 指定窗口推送消息
//     *
//     * @param entity 推送消息
//     * @param toId   接收方ID
//     */
//    //todo
//    public void sendTo(String toId, Message entity) {
//        fromId = entity.getFrom().getId().toString();
//        if (websocketServerEndpointSys.size() <= 1) {
//            throw new GlobalException("用户未上线");
//        }
//        boolean flag = false;
//        for (WebsocketServerEndpointSys_bak endpoint : websocketServerEndpointSys) {
//            try {
//                if (endpoint.fromId.equals(toId)) {
//                    flag = true;
//                    log.info(entity.getFrom().getId() + " 推送消息到窗口：" + toId + " ，推送内容：" + entity.getMessage());
//
//                    endpoint.sendMessage(getData(toId, entity.getMessage()));
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
//
//    private void subOnLineCount() {
//        WebsocketServerEndpointSys_bak.online--;
//    }
//
//    private synchronized long getOnlineCount() {
//        return online;
//    }
//
//    private void addOnlineCount() {
//        WebsocketServerEndpointSys_bak.online++;
//    }
//}
