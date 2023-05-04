package constant;

/**
 * 系统常量值
 *
 * @author tycoding
 * @date 2019-06-14
 */
public interface CommonConstant {

    /**
     * 用户数据 Key前缀标识
     */
    String USER_PREFIX = "USER_";

    /**
     * 系统发送给用户的 Key前缀标识 后面跟时间戳+用户id
     */
    String CHAT_SYS_PREFIX = "CHAT_SYS_";

    /**
     * 用户发送给用户的
     *      推送方Session Key前缀标识，后面跟会话id+发的用户id+收的用户id
     */
    String CHAT_USER_PREFIX = "CHAT_FROM_";

    /**
     * 推送至指定用户消息
     *      接收方Session Key前缀标识
     */
    String CHAT_TO_PREFIX = "_TO_";
    String CHAT_FROM_PREFIX = "chat_from";
    String CHAT_COMMON_PREFIX = "chat_common";

    /**
     * RedisTemplate 根据Key模糊匹配查询前缀
     */
    String REDIS_MATCH_PREFIX = "*";
    String[] Questions ={"1","时间","地点","活动类型",};//买了一个10升
}
