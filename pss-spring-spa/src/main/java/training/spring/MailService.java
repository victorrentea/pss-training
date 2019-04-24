package training.spring;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {
		private final static Logger log = LoggerFactory.getLogger(MailService.class);

	@Value("${mail.server}")
	private String emailServer;
	
	@Value("${mail.port}")
	private int emailPort;
	
	@Value("${mail.user}")
	private String emailUser;
	
	@Value("${mail.password}")
	private String emailPassword;
	
	@Autowired
	private MailConfiguration config;
	
	public void sendEmail() {
		log.debug("Trimit mail cu {}   {}   {}   {}",
				emailServer,
				emailPort,
				emailUser,
				emailPassword				
				);
		
		log.debug("Trimit mail cu {}   {}   {}   {}",
				config.server,
				config.port,
				config.user,
				config.password				
				);
	}
	
	@PostConstruct
	public void help() {
		sendEmail();
	}
}
