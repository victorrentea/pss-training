package ro.victor.training.jpa2;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.training.jpa2.domain.entity.ContactChannel;
import ro.victor.training.jpa2.domain.entity.ContactChannel.Type;
import ro.victor.training.jpa2.domain.entity.CourseActivity;
import ro.victor.training.jpa2.domain.entity.LabActivity;
import ro.victor.training.jpa2.domain.entity.StudentsGroup;
import ro.victor.training.jpa2.domain.entity.StudentsYear;
import ro.victor.training.jpa2.domain.entity.Subject;
import ro.victor.training.jpa2.domain.entity.Teacher;
import ro.victor.training.jpa2.domain.entity.TeachingActivity;

@Component
public class Ziua2 {
	@Autowired
	private EntityManager em;

	@Transactional
	public void firstTransaction() {
		Teacher teacher = new Teacher();
		teacher.getChannels().add(new ContactChannel(Type.TWITTER, "tw"));
		teacher.getChannels().add(new ContactChannel(Type.FACEBOOK, "fb"));
		
		Subject subject = new Subject("POO");
		teacher.addSubject(subject);
//		em.persist(subject);
		em.persist(teacher);
		
		TeachingActivity activity = new CourseActivity();
		subject.getActivities().add(activity);
		em.persist(activity);
		
		em.persist(new LabActivity());
		
		
	}
	@Transactional//(rollbackFor= {ExceptiaMeaEnervantaPeCareTotiiDeviiOStiuPeDeRost.class})
	public void secondTransaction() throws Exception {
		Teacher teacher = em.find(Teacher.class, 1L);
		System.out.println(teacher.getChannels());
		System.out.println(teacher.getHeldSubjects());
		
		
		TeachingActivity a = em.find(TeachingActivity.class, 1L);
		System.out.println("A:"+a);
	
		
		teacher.setName("Nume adevarat");
		em.flush();
		try {
//			altaMetoda(); // NU MERGE
//			totEu.altaMetoda(); // MERGE
//			altaClasa.altaMetodaDinAltaClasaCareNuPreiaTx(); // MERGE
//			altaClasa.altaMetodaDinAltaClasa(); // MERGe
		} catch (Exception e) {
			//shaorma
		}
		
	}
	@Autowired
	private Ziua2 totEu;
	
	@Autowired
	private AltaClasa2 altaClasa;
	
	@Transactional
	public void altaMetoda() {
		new RuntimeException().printStackTrace();
		throw new RuntimeException("Intentionat.");
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED) // DEFAULT!
	public void third() {
		 Subject subject = em.find(Subject.class, 1L);
		 System.out.println("Activities: "  + subject.getActivities());
//		 em.loc
		 subject.getActivities().clear();

		 
	
		 
		
	}
	
}

@Component
class AltaClasa2 {
	@Transactional
	public void altaMetodaDinAltaClasa() {
		new RuntimeException().printStackTrace();
		throw new RuntimeException("Intentionat.");
		
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void altaMetodaDinAltaClasaCareNuPreiaTx() {
		new RuntimeException().printStackTrace();
		throw new RuntimeException("Intentionat.");
		
	}
}
