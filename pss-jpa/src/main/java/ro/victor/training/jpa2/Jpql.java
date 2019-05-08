package ro.victor.training.jpa2;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.training.jpa2.domain.entity.CourseActivity;
import ro.victor.training.jpa2.domain.entity.Teacher;
import ro.victor.training.jpa2.domain.entity.TeachingActivity;

@Service 
public class Jpql {

	@Autowired
	private EntityManager em;	
	
	@Transactional
	public void play() {
		Teacher t = new Teacher("Giumale");
		em.persist(t);
		t.setName("Tavi");
		TeachingActivity curs = new CourseActivity();
		t.getActivities().add(curs);
		curs.getTeachers().add(t);
		em.persist(curs);
		 
		 Teacher tt = em.createQuery("SELECT t FROM Teacher t WHERE t.name = ?", Teacher.class)
				 .setParameter(1, "Tavi")
				 .getSingleResult();
		 System.out.println("Gata metoda : " + tt);
		 
		 System.out.println("Cursurile: " + 
				 em.createQuery("SELECT c FROM CourseActivity c JOIN c.teachers t WHERE t.name = ?", CourseActivity.class)
			 .setParameter(1, "Tavi")
			 .getResultList());
		 
	} 

}
