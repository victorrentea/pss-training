package ro.victor.training.jpa2;

import java.util.Random;

import javax.persistence.EntityManager;

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
		oop.setHolderTeacher(teacher); // doar asta MERGEE!! Dar lasa modelul din memorie inconsistent
		//ambele ar trebui sa fie legate ATOMIC
		
		
		
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
		
		teacher.getChannels().add(new ContactChannel(Type.TWITTER, "@victorrentea")); // 1 delete si 2 inserturi
	}

}
