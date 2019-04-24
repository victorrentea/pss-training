package training.spring.pssspringspa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AliasFor;

import training.spring.OrderPlacer;

@Configuration
public class ConfigDeTeste {


	//alternativa cu Polimorfism: sa scrii de mana o implem in memory -"test double"
//	@Bean
//	public IOrderPlacer orderPlacer() {
//		return new OrderPlacerFake();
//	}
	@AliasFor("orderPlacer")
	@Bean
	public OrderPlacer orderPlacerHack() {
		return new OrderPlacer() {
			public int logica() {
				System.out.println("Hack");
				return -2; 
			}
		};
	}
}
