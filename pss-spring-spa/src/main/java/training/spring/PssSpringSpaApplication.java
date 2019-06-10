package training.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@EnableScheduling
@EnableSwagger2
@SpringBootApplication
public class PssSpringSpaApplication {

	public static void main(String[] args) {
		SpringApplicaton.run(PssSpringSpaApplication.class, args);
	}

}
