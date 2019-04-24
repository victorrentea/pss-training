package training.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarfaController {
	private final static Logger log = LoggerFactory.getLogger(BarfaController.class);

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public void lanseaza(@RequestParam("b") String barfa) {
		log.info("Lansez");
		publisher.publishEvent(new Barfa(barfa));
		log.info("Regret ca am lansat");
	}
}

class Barfa{
	private final String barfa;
	public Barfa(String barfa) {
		this.barfa = barfa;
	}
	public String getBarfa() {
		return barfa;
	}
	public String toString() {
		return "Barfa [barfa=" + barfa + "]";
	}
}


// in alt pachet, departe, in Andromeda
@Component
class Raspandac {
	private final static Logger log = LoggerFactory.getLogger(Raspandac.class);
	@EventListener
	public BarfaDistorsionata asculta(Barfa barfa) {
		log.info("Aud: " + barfa);
		return new BarfaDistorsionata(barfa.getBarfa() + " distorsionata");
	}
}


class BarfaDistorsionata{
	private final String barfa;
	public BarfaDistorsionata(String barfa) {
		this.barfa = barfa;
	}
	public String getBarfa() {
		return barfa;
	}
	public String toString() {
		return "Barfa [barfa=" + barfa + "]";
	}
}

@Component
class UtilizatorFinal {
	private final static Logger log = LoggerFactory.getLogger(UtilizatorFinal.class);
	@EventListener
	public void asculta(BarfaDistorsionata barfa) {
		log.info("Aud final: " + barfa);
	}
}