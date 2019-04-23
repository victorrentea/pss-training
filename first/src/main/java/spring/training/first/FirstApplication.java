package spring.training.first;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	// @Primary // Oridecate ori Spring trebuie sa aleaga intre asta si altu, il ia pe asta !
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

@Retention(RetentionPolicy.RUNTIME)
@Component
@interface Facade {}

@Facade
//@Service
//@Repository
//@Controller
//@RestController
class A implements CommandLineRunner {
	@Autowired
	private B b;
	
	@Autowired
	private ApplicationContext spring;
	
	@Autowired
	@Qualifier("vlad")
	private Person person;
	
	@Autowired
	private List<Person> totiCopii;

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
		if (Math.random() > 0.5) {
			System.out.println(spring.getBean("dacaEra")); // URAT!!! cum sa pui nume de clasa intre "ghilimele"
			// getBean(String) facut la runtime e periculos, ca poate nu cazi in eroare
			// decat in Productie
			// spring.getBean("dacaEraXXX")
			DacaEra dacaEra = spring.getBean(DacaEra.class);
			System.out.println("Bean luat dupa tip: " + dacaEra);
		}
//		System.out.println(spring.getBean("dacaEra"));
		
		System.out.println("CopilUL: " + person);
		
		System.out.println("Toti copiii: " + totiCopii);
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
	public String toString() {
		return "Person [name=" + name + "]";
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

