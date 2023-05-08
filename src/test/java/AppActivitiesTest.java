
import cn.tycoding.PartnerApplication;
import cn.tycoding.pojo.AppActivities;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

//@Test
@SpringBootTest(classes = PartnerApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
@EnableConfigurationProperties({AppActivities.class})
class AppActivitiesTest {
    @Autowired //将person自动注入进来
    private AppActivities appActivities;
    @Test
    void contextLoads() {
        System.out.println(Arrays.toString(appActivities.getActivities().toArray())); //打印看下person对象
    }
//    @Test
//    void getArray(){
//        System.out.println(appActivities.getActivities().getClass());
//        ArrayList<AppActivities.Activity> aa= (ArrayList<AppActivities.Activity>) appActivities.getActivities(); // 类的转化
//        for( AppActivities.Activity  xx: aa){
//            System.out.print(xx.getScheme() );
//        }
//
//        int[] ns = { 68, 79, 91, 85, 62 };
//        for(Integer number : ns){
//            System.out.print(number + " ");
//        }
//
//
//    }
}
