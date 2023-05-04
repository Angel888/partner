package cn.tycoding.service;

import cn.tycoding.entity.Message;
import cn.tycoding.entity.User;
import cn.tycoding.pojo.AppActivities;
import cn.tycoding.util.CoreUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import constant.CommonConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

import static constant.CommonConstant.Questions;


@Slf4j
@Service
@RequiredArgsConstructor
public  class ChatSessionService {


    private final StringRedisTemplate redisTemplate;
    private final AppActivities appActivities;
    private Session session;
    private Integer conversationNum = 0;
    private String fromId = "";
    private Integer userId ;
//    appActivities.getActivities() //todo 为什么不能写到这里
    /**
     * 推送消息
     *
     * @param message
     */

    public void SendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    public void OnOpenSys(String conversation_id,Session session){
        this.session=session;
        this.conversationNum += 1;
        //将当前websocket对象存入到Set集合中

        //在线人数+1
//        addOnlineCount();

        this.fromId = CommonConstant.USER_PREFIX + conversation_id;
//        this.userId=
//        log.info("chatSessionService ----",chatSessionService.toString());  //todo  chatSessionService为啥是null?
        if (this.hasKey(this.fromId)){
            this.delete(this.fromId);
        }
        this.rightPush(this.fromId,  "连接成功，你好！");
        try {
            SendMessage("连接成功，你好！");  //todo sendMessage需要加this吗？
        } catch (IOException e) {
            log.error("websocket IO异常");
        }

    }
    public void OnMessageSys(String fromId ,String message) throws IOException {
        this.conversationNum= Math.toIntExact(this.getSize(this.fromId));
        this.rightPush(this.fromId,  message);
        this.SendMessage(Questions[this.conversationNum]);
    }
    public User findById(String id) {
        if (id != null) {
            String value = null;
            if (id.startsWith(CommonConstant.USER_PREFIX)) {
                value = redisTemplate.boundValueOps(id).get();
            } else {
                value = redisTemplate.boundValueOps(CommonConstant.USER_PREFIX + id).get();
            }
            JSONObject object = JSONObject.parseObject(value);
            if (object != null) {
                return object.toJavaObject(User.class);
            }
        }
        return null;
    }

    public void pushMessage(String fromId, String toId, String message) {
        Message entity = new Message();
        entity.setMessage(message);
        entity.setFrom(this.findById(fromId));
        entity.setTime(CoreUtil.format(new Date()));
        if (toId != null) {
            //查询接收方信息
            entity.setTo(this.findById(toId));
            //单个用户推送
//        } else {
//            //公共消息 -- 群组
//            entity.setTo(null);
//            push(entity, CommonConstant.CHAT_COMMON_PREFIX + fromId);
//        }
        }
    }

    /**
     * 推送消息
     *
     * @param entity Session value
     * @param key    Session key
     */
    private void push(Message entity, String key) {
        //这里按照 PREFIX_ID 格式，作为KEY储存消息记录
        //但一个用户可能推送很多消息，VALUE应该是数组
        List<Message> list = new ArrayList<>();
        String value = redisTemplate.boundValueOps(key).get();
        if (value == null) {
            //第一次推送消息
            list.add(entity);
        } else {
            //第n次推送消息
            list = Objects.requireNonNull(JSONObject.parseArray(value)).toJavaList(Message.class);
            list.add(entity);
        }
        redisTemplate.boundValueOps(key).set(JSONObject.toJSONString(list));
    }

    public List<User> onlineList() {
        List<User> list = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(CommonConstant.USER_PREFIX + CommonConstant.REDIS_MATCH_PREFIX);
        if (keys != null && keys.size() > 0) {
            keys.forEach(key -> {
                list.add(this.findById(key));
            });
        }
        return list;
    }

    public List<Message> commonList() {
        List<Message> list = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(CommonConstant.CHAT_COMMON_PREFIX + CommonConstant.REDIS_MATCH_PREFIX);
        if (keys != null && keys.size() > 0) {
            keys.forEach(key -> {
                String value = redisTemplate.boundValueOps(key).get();
                List<Message> messageList = Objects.requireNonNull(JSONObject.parseArray(value)).toJavaList(Message.class);
                list.addAll(messageList);
            });
        }
//        CoreUtil.sort(list);
        return list;
    }

    public List<Message> selfList(String fromId, String toId) {
        List<Message> list = new ArrayList<>();
        //A -> B
        String fromTo = redisTemplate.boundValueOps(CommonConstant.CHAT_FROM_PREFIX + fromId + CommonConstant.CHAT_TO_PREFIX + toId).get();
        //B -> A
        String toFrom = redisTemplate.boundValueOps(CommonConstant.CHAT_FROM_PREFIX + toId + CommonConstant.CHAT_TO_PREFIX + fromId).get();

        JSONArray fromToObject = JSONObject.parseArray(fromTo);
        JSONArray toFromObject = JSONObject.parseArray(toFrom);
        if (fromToObject != null) {
            list.addAll(fromToObject.toJavaList(Message.class));
        }
        if (toFromObject != null) {
            list.addAll(toFromObject.toJavaList(Message.class));
        }

        if (list.size() > 0) {
//            CoreUtil.sort(list);
            return list;
        } else {
            return new ArrayList<>();
        }
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
