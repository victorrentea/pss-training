package spring.training.lifecycle;

import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.stereotype.Service;

@EnableCaching
@SpringBootApplication
public class SingletonSpringApp implements CommandLineRunner{
	@Bean
	public static CustomScopeConfigurer defineThreadScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		configurer.addScope("thread", new SimpleThreadScope()); // WARNING: Leaks memory. Prefer 'request' scope or read here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/SimpleThreadScope.html 
		return configurer;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SingletonSpringApp.class);
	}
	
	@Autowired 
	private OrderExporter exporter;
	
	// TODO [1] make singleton; test multi-thread: state is [ | | | ]
	// TODO [2] instantiate manually, set dependencies, pass around; no AOP
	// TODO [3] prototype scope + ObjectFactory or @Lookup. Did you said "Factory"? ...
	// TODO [4] thread/request scope. HOW it works?! Leaks: @see SimpleThreadScope javadoc
	// TODO [5] (after AOP): RequestContext, @Cacheable. on thread?! @ThreadLocal
	public void run(String... args) throws Exception {
		exporter.export(Locale.ENGLISH);
		exporter.export(Locale.FRENCH);
	}
}

@Service
class OrderExporter  {
	private final static Logger log = LoggerFactory.getLogger(OrderExporter.class);
	@Autowired
	private InvoiceExporter invoiceExporter;
	@Autowired
	private LabelService labelService;

	public void export(Locale locale) {
		labelService.load(locale);
		log.debug("Running export in " + locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO")); 
		invoiceExporter.exportInvoice();
	}
}

@Service 
class InvoiceExporter {
	private final static Logger log = LoggerFactory.getLogger(InvoiceExporter.class);
	@Autowired
	private LabelService labelService;
	
	public void exportInvoice() {
		log.debug("Invoice Country: " + labelService.getCountryName("ES"));
	}
}

@Service
class LabelService {
	private final static Logger log = LoggerFactory.getLogger(LabelService.class);
	@Autowired
	private CountryRepo countryRepo;
	
	public LabelService(CountryRepo countryRepo) {
		System.out.println("+1 Label Service");
		this.countryRepo = countryRepo;
	}

	private Map<String, String> countryNames;
	
	public void load(Locale locale) {
		countryNames = countryRepo.loadCountryNamesAsMap(locale);
	}
	
	public String getCountryName(String iso2Code) {
		log.debug("getCountryName() on instance: " + this.hashCode());
		return countryNames.get(iso2Code.toUpperCase());
	}
}