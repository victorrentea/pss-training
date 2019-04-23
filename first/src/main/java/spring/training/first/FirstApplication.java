package spring.training.first;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class FirstApplication {
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
	
	public void run(String... args) throws Exception {
		System.out.println("Hello Spring Boot!");
		b.bye();
		
	}
}
@Component
class B {
	private C c;
	private D d;
	// Daca Spring vede o clasa @Component/Service care are un singur constructor 
	// va incerca automat sa apeleze acel constructor cu bean-uri compatibile din "galeata"
	public B(C c, D d) {
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
class D {}

