
import cn.tycoding.PartnerApplication;
import cn.tycoding.pojo.AppActivities;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

//@Test
@SpringBootTest(classes = PartnerApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class AppActivitiesTest {
    @Autowired //将person自动注入进来
    private AppActivities appActivities;
    @Test
    void contextLoads() {
        System.out.println(Arrays.toString(appActivities.getActivities().toArray())); //打印看下person对象
    }
    @Test
    void getArray(){
        System.out.println(appActivities.getActivities().getClass());
        AppActivities.Activity[]=appActivities.getActivities();
    }
}
