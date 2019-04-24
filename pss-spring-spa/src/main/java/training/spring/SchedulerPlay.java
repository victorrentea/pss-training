package training.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerPlay {
	private final static Logger log = LoggerFactory.getLogger(SchedulerPlay.class);

//	@Scheduled(cron = "0/5 * * * * *")
	public void helloNarcis() throws InterruptedException {
		log.debug("Ii spun acush...");
		Thread.sleep(6000);
		log.debug("Ce frumos esti!");
	}
	
//	@Scheduled(cron = "${cron.expr}")
	public void hater() throws InterruptedException {
		log.debug("Ii spun acush...");
		Thread.sleep(6000);
		log.debug("Urat esti mah!");
	}
	
}
