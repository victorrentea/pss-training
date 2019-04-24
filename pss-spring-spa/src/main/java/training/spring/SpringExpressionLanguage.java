package training.spring;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringExpressionLanguage {
	
	@Autowired
	public void letsplay(@Value("#{(date.getTeatru()?.toUpperCase()?:'nada')[1]}") String porturi) {
		System.out.println("Spring EL: " + porturi); 
	}
	@Autowired
	public void letsplay2(@Value("#{date.teatru.split(' ')}") List<String> nameParts) {
		System.out.println("Porturi: " + nameParts); 
	}
	
//	@Autowired
//	public void letsplay(@Value("${mapa.frate}") Map<String, Integer> mapa) {
//		System.out.println("Mapa: " + mapa); 
//	}

}


@Component
class Date {
	@Value("${teatru}")
	private String teatru;
	
	public String getTeatru() {
		
		return teatru;
	}
}
