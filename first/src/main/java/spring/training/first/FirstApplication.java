package spring.training.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class FirstApplication {
	
	//cream manual o instanta de Person invocand dupa gust constructorul, 
	//sau facand ce alte initializari mai aveam nevoie
	@Bean
	public Person emma() {
		return new Person("Emma");
	}
	@Bean
	public Person vlad() {
		return new Person("Vlad");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}
}

@Component
//@Service
//@Repository
//@Controller
//@RestController
class A implements CommandLineRunner {
	@Autowired
	private B b;
	
	@Autowired
	private ApplicationContext spring;

	public void run(String... args) throws Exception {
		System.out.println("Hello Spring Boot!");
		b.bye();
		
		//acest nume de bean rezulta din numele metodei marcate cu @Bean "emma()". Fratzicule!
		Person emma = (Person) spring.getBean("emma");
		System.out.println("Pe fii-mea o cheama : " + emma.getName());
		Person vlad = (Person) spring.getBean("vlad");
		System.out.println("Pe fii-miu o cheama : " + vlad.getName());
		
		//by default, numele oricarui @Component/... adaugat 
		// automat este lower-CamelCase al numelui clasei
		System.out.println(spring.getBean("dacaEra"));
	}
}


class Person {
	private final String name;
	public Person(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}


@Component
class B {
	private C c;
	private DacaEra d;
	// Daca Spring vede o clasa @Component/Service care are un singur constructor 
	// va incerca automat sa apeleze acel constructor cu bean-uri compatibile din "galeata"
	public B(C c, DacaEra d) {
		this.c = c;
		this.d = d;
	}
	public void bye() {
		System.out.println("Bye Spring Boot!");
	}
}

@Component
class C {}
@Component
class DacaEra {}

