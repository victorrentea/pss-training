package victor.training.spring.tx;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MyConfig.class)
public class TransactionsAllAround {

    @Autowired
    private ServiceWithTx service;

    @Test
    public void dummy() {
        service.doInTransaction();
    }



}

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "victor.training.spring.tx")
class MyConfig {
}


@Service
@Transactional
class ServiceWithTx {
    public void doInTransaction() {
        new RuntimeException().printStackTrace();
    }
}

@Aspect
@Component
class DoMoreBeforeTx {
    @Around("exection(* *(..)) && @within(org.springframework.transaction.annotation.Transactional)")
    public void doStuffBeforeTx() {

    }
}