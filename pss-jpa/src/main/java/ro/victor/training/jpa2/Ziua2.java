package ro.victor.training.jpa2;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.training.jpa2.domain.entity.ContactChannel;
import ro.victor.training.jpa2.domain.entity.LabActivity;
import ro.victor.training.jpa2.domain.entity.ContactChannel.Type;
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
		
		em.persist(new LabActivity());
		
	}
	@Transactional//(rollbackFor= {ExceptiaMeaEnervantaPeCareTotiiDeviiOStiuPeDeRost.class})
	public void secondTransaction() {
		Teacher teacher = em.find(Teacher.class, 1L);
		System.out.println(teacher.getChannels());
		System.out.println(teacher.getHeldSubjects());
		
		TeachingActivity a = em.find(TeachingActivity.class, 1L);
		System.out.println("A:"+a);
		
		
		teacher.setName("Nume adevarat");
		
		
	}
}
