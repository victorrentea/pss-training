package victor.training.spring;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import victor.training.spring.service.MyWSClient;

import java.util.Arrays;

@Configuration
public class TestConfig {


    @Bean
    public MyWSClient myWSClient() {
        return Mockito.mock(MyWSClient.class);
    }

}
