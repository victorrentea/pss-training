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
		em.persist(new ErrorLog("Eroarea 2"));
		
	}

	@Transactional
	public void secondTransaction() {
		ErrorLog errorLog = em.find(ErrorLog.class, 1L);
		System.out.println("mesaj: " + errorLog.getMessage());
		errorLog.setMessage("Alt Mesaj");

		em.remove(em.find(ErrorLog.class, 2L));
	}

}
