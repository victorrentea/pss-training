package spring.training.proxy;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching 
@SpringBootApplication
public class ProxySpringApp  {
	private final static Logger log = LoggerFactory.getLogger(ProxySpringApp.class);
	public static void main(String[] args) {
		SpringApplication.run(ProxySpringApp.class, args);
	}
	
	@Bean
	public BeanPostProcessor cacheAugmenter() {
		return new BeanPostProcessor() {
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//				if (bean.getClass().isAnnotationPresent(Facade.class)) {
//					log.debug("Wrapping in a proxy " + bean.getClass());
//					return ClassProxy.proxy(bean);
//				}
//				else {
					return bean;
//				}
			}
		};
	}
}
@Component
class Runner implements CommandLineRunner {
	private final static Logger log = LoggerFactory.getLogger(Runner.class);
	// [1] Implement decorator with Spring  
	// [2] InterfaceProxy.proxy (no Spring)
	// [3] ClassProxy.proxy (no Spring)
	// [4] Spring Cache support
	// [5] Spring aspect, @Facade, @Logged1 
	// [6] Tips: self proxy, debugging, final
	// [7] OPT: Manual proxying using BeanPostProcessor23
	
	public void run(String... args) throws Exception {
		ExpensiveOps ops =new ExpensiveOps();
		log.debug("\n");
 		log.debug("---- CPU Intensive ~ memoization?");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10_000_169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");
		
		log.debug("---- I/O Intensive ~ \"There are only two things hard in programming...\"");
		log.debug("Folder . MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		log.debug("Folder . MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
	}
}
