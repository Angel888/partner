package cn.tycoding.pojo;

/**
 * Copyright 2023 bejson.com
 */

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2023-05-08 13:28:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Slf4j
@Data
//todo 这两个注解有必要有吗？
@Configuration//注册bean到容器中
@ConfigurationProperties(prefix = "application")
public class AppActivities {

    public List<Activity> activities;  //todo 需要写new方法吗？

    public String debug;

    public ArrayList<String> GetScheme() {
        ArrayList<String> res = new ArrayList<String>();
        log.info("this.getActivities()---", this.getActivities().toString());
        ArrayList<Activity> aa = (ArrayList<Activity>) this.getActivities(); // 类的转化
        // todo aa是null?
        for (Activity xx : aa) {
            res.add(xx.getScheme());
        }
        return res;
    }


}

