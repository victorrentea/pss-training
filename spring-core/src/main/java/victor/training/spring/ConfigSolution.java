package victor.training.spring;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import victor.training.spring.service.CurrencyConverterHandshakeImpl;
import victor.training.spring.service.CurrencyConverterStatelessImpl;
import victor.training.spring.service.HRService;
import victor.training.spring.service.MyWSClient;

import javax.annotation.PostConstruct;
import javax.jws.Oneway;
import java.util.Arrays;

@Configuration
@ComponentScan("victor.training.spring")
@ImportResource("classpath:config-basic.xml")
public class ConfigSolution {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        return source;
    }

    @Bean
    @Lazy
    public CurrencyConverterHandshakeImpl handshakeConverter() {
        System.out.println("Once!");
        CurrencyConverterHandshakeImpl bean = new CurrencyConverterHandshakeImpl();
//        bean.initialize();;
        return bean;
    }


    @Bean
    public HRService hrService(@Value("${myProperty}") String myProperty) {

        System.out.println("myProperty="+myProperty);
//    public HRService hrService(@Value("${myProperty}") String myProperty, MyWSClient myWSClient) {
        HRService service = new HRService();
//        service.setWebServiceClient(myWSClient);
        service.setWebServiceClient(myWSClient());
        service.setMyProperty(myProperty);
        return service;
    }

    @Bean
    public MyWSClient myWSClient() {
        MyWSClient wsClient = MyWSClient.getInstance();
        wsClient.setEndpointURLs(Arrays.asList("http://ro.ibm.com/myws", "http://localhost:8080/myws"));
        return wsClient;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:file:hr-database");
        dataSource.setUsername("SA");
        dataSource.setPassword("");
        return dataSource;
    }







    @Bean
    public Surprise surprise() {
        System.out.println("Here1, I start creating the surprise");
        return new Surprise(handshakeConverter());
    }

    @Bean
    public Surprise surprise2() {
        System.out.println("Here2, I start creating the surprise");
        return new Surprise(handshakeConverter());
    }

    @Bean
    public CurrencyConverterStatelessImpl statlessConverter() {
        return new CurrencyConverterStatelessImpl();
    }


}

class Surprise {
    private final CurrencyConverterHandshakeImpl dep;

    Surprise(CurrencyConverterHandshakeImpl dep) {
        this.dep = dep;
    }
}

@Component
@Lazy
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS) // Magic! Only create the lazy on its first call of a method
class LazyStuff {

    LazyStuff() {
        System.out.println("Creating a Lazy");
    }

    public void m() {
    }
}

@Component
class DoingStuff {
    @Autowired
    private LazyStuff lazy;

    @PostConstruct
    public void wakeUp() {
        System.out.println("Start ");
        System.out.println("After");
        lazy.m();
        System.out.println("After call");
    }
}