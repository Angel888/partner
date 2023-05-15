/**
 * Copyright 2023 bejson.com
 */
package cn.tycoding.dto;
import cn.tycoding.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

/**
 * Auto-generated: 2023-05-07 12:43:35
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
//@AllArgsConstructor //生成全参数构造函数
public class WsMessage { //todo  这个类需不需要写成有参构造的形式？
    enum MessageType {
        Text,
        SingleChoice,
        MultiChoice
    }
    enum EndType{
        System,
        Person
    }
    private String session;
    private String timestamp;
    private String msgId;
    private String sessionId;
    private String from; // 系统消息是1
    private String to;
    private MessageType type;
    private String message;
    private List<String> options;
    private UserInfo userInfo;
}
