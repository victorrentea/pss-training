package spring.training.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class StrategySpringApp implements CommandLineRunner {
	public static void main(String[] args) {
		new SpringApplicationBuilder(StrategySpringApp.class)
			.profiles("localProps")
			.run(args);
	}

	
	private ConfigProvider configProvider = new ConfigFileProvider(); 
	
	@Autowired
	private CustomsService service;
	// TODO [1] Break CustomsService logic into Strategies
	// TODO [2] Convert it to Chain Of Responsibility
	// TODO [3] Wire with Spring
	// TODO [4] ConfigProvider: selected based on environment props, with Spring
	public void run(String... args) throws Exception {
		System.out.println("Tax for (RO,100,100) = " + service.computeCustomsTax("RO", 100, 100));
		System.out.println("Tax for (CH,100,100) = " + service.computeCustomsTax("CH", 100, 100));
		System.out.println("Tax for (UK,100,100) = " + service.computeCustomsTax("UK", 100, 100));
		
		System.out.println("Property: " + configProvider.getProperties().getProperty("someProp"));
	}
}
@Service
class CustomsService {
	@Autowired
	private UKTaxComputer uk;
	
	@Autowired
	private EUTaxComputer eu;
	
	@Autowired
	private CHTaxComputer ch;
	
	public double computeCustomsTax(String originCountry, double tobacoValue, double regularValue) { // UGLY API we CANNOT change
		switch (originCountry) { 
		case "UK": return uk.computeUKTax(tobacoValue, regularValue); 
		case "CH": return ch.computeCHTax(tobacoValue, regularValue);
		case "FR": 
		case "ES": // other EU country codes...
		case "RO": return eu.computeEUTax(tobacoValue);
		default: throw new IllegalArgumentException("Not a valid country ISO2 code: " + originCountry);
		} 
	}
}
@Service
class W{ }
@Service
class EUTaxComputer {
	@Autowired
	private W w;
	public double computeEUTax(double tobacoValue) {
		System.out.println("w: " + w);
		return tobacoValue/3;
	}
}
@Service
class CHTaxComputer {
	public double computeCHTax(double tobacoValue, double regularValue) {
		return tobacoValue + regularValue;
	}
}
@Service
class UKTaxComputer {
	public double computeUKTax(double tobacoValue, double regularValue) {
		return tobacoValue/2 + regularValue/2;
	}
}