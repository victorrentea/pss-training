package ro.victor.training.jpa2;

import java.util.Random;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.training.jpa2.domain.entity.ErrorLog;

@Component
public class Playground {
	
	@Autowired
	private EntityManager em;

	@Transactional
	public void firstTransaction() {
		em.persist(new ErrorLog("Defect Driven Development"));
		
		
	}

	public void secondTransaction() {
		 // TODO
	}

}
