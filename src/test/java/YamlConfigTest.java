import cn.tycoding.PartnerApplication;
import cn.tycoding.pojo.AppActivities;
import cn.tycoding.pojo.YamlConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = PartnerApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
@EnableConfigurationProperties({AppActivities.class})
public class YamlConfigTest {
    @Autowired
    private YamlConfig myConfig;
    @Test
    public void run(String... args) throws Exception {
        System.out.println("using environment: " + myConfig.getEnvironment());
        System.out.println("name: " + myConfig.getName());
        System.out.println("enabled:" + myConfig.isEnabled());
        System.out.println("servers: " + myConfig.getServers());
    }
}
