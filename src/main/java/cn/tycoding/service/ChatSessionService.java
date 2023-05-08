package cn.tycoding.service;

import cn.tycoding.dto.WsMessage;
import cn.tycoding.pojo.AppActivities;
import cn.tycoding.util.R;
import com.alibaba.fastjson.JSON;
import constant.CommonConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatSessionService {


    private final StringRedisTemplate redisTemplate;
    private  final AppActivities appActivities;  //todo 可以不加@Autowired，使用final吗？
    private Session session;
    private Integer conversationTimes = 0;
    private String fromId = "";
    private final List<AppActivities.Activity> activities = appActivities.getActivities(); // activity列表

    private final ArrayList<String> schemes = appActivities.GetScheme(); //获取scheme列表
    private String scheme;
    private List<AppActivities.Field> fieldList;
    private String ConversationId ;

    /**
     * 推送消息
     *
     * @param message
     */

    public void SendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
// todo 因为SendMessage有IOException，所以OnOpenSys和引用OnOpenSys的onOpen都得有吗？这样是不是不优雅？？
    public R OnOpenSys(String conversation_id, Session session) throws IOException {
        this.ConversationId=ConversationId;
        this.session = session;  //todo 这个session为什么不需要初始化？
        // 通过stream流收集的方式
//        List<String> schemes = this.appActivities.getActivities().stream().map(AppActivities.Activity::getScheme).collect(Collectors.toList());


        this.fromId = CommonConstant.USER_PREFIX + conversation_id;
//        log.info("chatSessionService ----",chatSessionService.toString());  //todo  chatSessionService为啥是null?
        if (this.hasKey(this.fromId)) {
            this.delete(this.fromId);
        }
        this.rightPush(this.fromId, "连接成功，你好！");
        SendMessage("连接成功，你好！");  //todo sendMessage需要加this吗？

        WsMessage message;
//            SendMessage((String)this.schemes[conversationTimes]);
            message = new WsMessage();
            message.setSessionId(this.session.getId());
            message.setTimestamp(String.valueOf(System.currentTimeMillis()));
            message.setMsgId(conversation_id);
            message.setFrom(String.valueOf(1));
            message.setTo(conversation_id);
            message.setFrom(this.session.getId());
            message.setMessage("活动类型？"); //todo 需要把list转为string吗？
            message.setOptions(schemes);
        String messageStr = JSON.toJSONString(message);
        SendMessage(messageStr);
        return null;
    }

    public void OnMessageSys(String fromId, String message) throws IOException {
//        this.conversationTimes= Math.toIntExact(this.getSize(this.fromId));
//        this.rightPush(this.fromId, message);
        if (conversationTimes == 0) {
            this.scheme = message;
            activities.forEach(item -> {
                if (item.getScheme() == this.scheme) {
                    this.fieldList = item.getFields();
                }
                try {
                    this.SendMessage(this.fieldList.get(0).getQuestion());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }else if (conversationTimes < this.fieldList.size()) {
            ConstructWsMessage(this.fieldList.get(conversationTimes));
            this.SendMessage(this.fieldList.get(conversationTimes).getQuestion());
            this.conversationTimes += 1;
        }
    }

    public String ConstructWsMessage(AppActivities.Field field ){
        WsMessage message = new WsMessage();
        if (field.getOptions()!=null){
            message.setOptions(field.getOptions());

        }
//            SendMessage((String)this.schemes[conversationTimes]);
        message = new WsMessage();
        message.setSessionId(this.session.getId());
        message.setTimestamp(String.valueOf(System.currentTimeMillis()));
        message.setMsgId(this.ConversationId);
        message.setFrom(String.valueOf(1));
        message.setTo(this.ConversationId);
        message.setFrom(this.session.getId());
        message.setMessage("活动类型？"); //todo 需要把list转为string吗？
        return JSON.toJSONString(message);
    }

    public void delete(String id) {
        if (id != null) {
            log.info("从Redis中删除此Key: " + id);
            redisTemplate.delete(CommonConstant.USER_PREFIX + id);
        }
    }

    public boolean hasKey(String keyName) {
//            return redisTemplate.hasKey("keyName");  //todo 为什么这样写NullPoniterException?
        return Boolean.TRUE.equals(redisTemplate.hasKey(keyName));
    }

    public Long getSize(String keyName) {
        return redisTemplate.opsForList().size(keyName);
    }

    public void rightPush(String keyName, String valueName) {
        redisTemplate.opsForList().rightPush(keyName, valueName);  //todo  这种的需要抛出异常吗
    }




}
