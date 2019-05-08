package ro.victor.training.jpa2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableTransactionManagement
@EnableJpaAuditing
public class Jpa2Application {

//	@Autowired
//	private DummyDataCreator dummyDataCreator;
//	@Autowired
//	private Playground playground;
	@Autowired
	private Ziua2 playground;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("Application started. Running playground code...");
//		dummyDataCreator.persistDummyData();
		System.out.println(" ========= FIRST TRANSACTION ========== ");
		playground.firstTransaction();
		System.out.println(" ========= SECOND TRANSACTION ========== ");
		try {
			playground.secondTransaction();
		} catch (Throwable tot) {
			//shaorma
		}
		System.out.println(" ========= THIRD TRANSACTION ========== ");
		playground.third();
//		System.out.println(" ========= forth TRANSACTION ========== ");
//		playground.forth();
		System.out.println(" ========= END ========== ");
	}
	

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		SpringApplication.run(Jpa2Application.class, args);
	}
}
