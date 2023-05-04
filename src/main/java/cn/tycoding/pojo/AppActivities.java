package cn.tycoding.pojo;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Setter
@Getter
//todo 这两个注解有必要有吗？

@Configuration//注册bean到容器中
@ConfigurationProperties(prefix = "application")
@EnableConfigurationProperties
public class AppActivities {


    private List<Activity> activities; //todo 到底用private还是public?

    @Setter
    @Getter
    private static class Activity {
        private String scheme;
        private List<Field> fields;

        @Override
        public String toString() {
            return "Activity{" +
                    "scheme='" + scheme + '\'' +
                    ", fields=" + fields +
                    '}';
        }
    }

    @Setter
    @Getter
    private static class Field {
        private String name;
        private String question;
        private String options;

        @Override
        public String toString() {
            return "Field{" +
                    "name='" + name + '\'' +
                    ", question='" + question + '\'' +
                    ", options='" + options + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AppActivities{" +
                "activities=" + activities +
                '}';
    }
}


