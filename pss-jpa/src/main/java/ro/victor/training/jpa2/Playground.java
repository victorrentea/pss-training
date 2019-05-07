package ro.victor.training.jpa2;

import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.TransactionScoped;

import org.h2.engine.SysProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.training.jpa2.domain.entity.ContactChannel;
import ro.victor.training.jpa2.domain.entity.ContactChannel.Type;
import ro.victor.training.jpa2.domain.entity.ErrorLog;
import ro.victor.training.jpa2.domain.entity.Subject;
import ro.victor.training.jpa2.domain.entity.Teacher;
import ro.victor.training.jpa2.domain.entity.TeacherDetails;

@Component
public class Playground {
	
	@Autowired
	private EntityManager em;

	@Transactional
	public void firstTransaction() {
		System.out.println("Oare EntityManager chiar e un proxy ?" + em.getClass());
		em.persist(new ErrorLog("Defect Driven Development"));
		em.persist(new ErrorLog("Eroarea 2"));
		
		
		Teacher teacher = new Teacher("Dragos");
		em.persist(teacher);
		
		teacher.getCounselingTimeSlot().setStartHour(10);
		teacher.getCounselingTimeSlot().setDurationInHours(2);
		System.out.println("Oare cand se face update-ul in baza ?!");
//		new RuntimeException("Ca sa vezi TransactionInterceptor").printStackTrace();
		
		TeacherDetails details = new TeacherDetails();
//		em.persist(details); 
		// 1) fie persist explicit obiectele noi pe care le 
		// vreau inserate impreuna cu parintele
		// 2) fie cascadez .persist()-ul pe copii Teacher.details->@OneToOne(cascade=PERSIST)
		teacher.setDetails(details);
		
		teacher.getChannels().add(new ContactChannel(Type.FACEBOOK, "bigbrother"));
		
		Subject oop = new Subject("OOP");
		
		
//		teacher.getHeldSubjects().add(oop); // doar asta nu merge
//		oop.setHolderTeacher(teacher); // doar asta MERGEE!! Dar lasa modelul din memorie inconsistent
		//ambele ar trebui sa fie legate ATOMIC
		
		teacher.addSubject(oop); // legaturile bi-directionale sunt setate 'atomic'
		
		
		em.persist(oop);
	}
	
	@Transactional
	public void secondTransaction() {
		ErrorLog errorLog = em.find(ErrorLog.class, 1L);
		System.out.println("mesaj: " + errorLog.getMessage());
		errorLog.setMessage("Alt Mesaj");
		

		em.remove(em.find(ErrorLog.class, 2L));
		
		Teacher teacher = em.find(Teacher.class, 1L);
		teacher.getDetails().setCv("CV umflat");
		
		System.out.println("t=" + teacher);
		System.out.println("Ia sa iau io din nou teacherul");
		System.out.println("t=" + em.find(Teacher.class, 1L));
		em.refresh(teacher);
		System.out.println("Pentru Vali " + teacher);
		
		teacher.getChannels().add(new ContactChannel(Type.TWITTER, "@victorrentea")); // 1 delete si 2 inserturi
		System.out.println("Borna");
		Set<Subject> subjectList = teacher.getHeldSubjects();
		System.out.println("am luat lista: " + subjectList.getClass());
		System.out.println("Ma uit la : " + subjectList);
		
		em.flush();
		System.out.println("Sfarsitul metodei");
		// rupem tranzactia aici. o facem sa crape
		throw new RuntimeException("dummy");
	}
	
	// NO TRANSACTION
	public void third() {
//		Teacher teacher = em.find(Teacher.class, 1L);// nu merge: 
		//org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: ro.victor.training.jpa2.domain.entity.Teacher.heldSubjects, could not initialize proxy - no Session

		Teacher teacher = em.createQuery(
				"SELECT t FROM Teacher t LEFT JOIN FETCH t.heldSubjects WHERE t.id = ?", 
				Teacher.class)
				.setParameter(1, 1L)
				.getResultList()
				.get(0);
		System.out.println("Borna");
		System.out.println("Ma uit la : " + teacher.getHeldSubjects());
	}
	
	@Transactional
	public void forth() {
		System.out.println("Eroarea1: " + em.find(ErrorLog.class, 1L));
		altaClasa.altaMetoda();
	}
	@Autowired
	private AltaClasa altaClasa;
	
	
}

@Component
class AltaClasa {
	@Autowired
	private EntityManager em;

	public void altaMetoda() {
		System.out.println("Eroarea1bis: " + em.find(ErrorLog.class, 1L));
	}
}
