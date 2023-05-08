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


    @Data
    public class Activity {
        private String scheme;
        private List<Field> fields;
    }

    @Data
    public class Field {
        private String name;
        private String question;
        private List<String> options = new ArrayList<>();
    }

    public ArrayList<String> GetScheme() {
        ArrayList<String> res = new ArrayList<String>();
        log.info("this.getActivities()---", this.getActivities().toString());
        ArrayList<AppActivities.Activity> aa = (ArrayList<AppActivities.Activity>) this.getActivities(); // 类的转化
        // todo aa是null?
        for (AppActivities.Activity xx : aa) {
            res.add(xx.getScheme());
        }
        return res;
    }


}

