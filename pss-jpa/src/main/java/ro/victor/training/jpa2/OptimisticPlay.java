package ro.victor.training.jpa2;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.training.jpa2.domain.entity.StudentsGroup;
import ro.victor.training.jpa2.domain.entity.StudentsYear;

@Component
public class OptimisticPlay {

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED) // DEFAULT!
	public void firstTransaction() {
		// creere
		StudentsGroup group = new StudentsGroup("CA321");
		em.persist(group);
		System.out.println("Oare ce valoare mi-a pus JPA pe coloana version ? : " + group.getVersion());

	}

	@Transactional(propagation = Propagation.REQUIRED) // DEFAULT!
	public void secondTransaction() {

		// primul user vine si face:
		StudentsYear year = new StudentsYear();
		em.persist(year);
		StudentsGroup group2 = new StudentsGroup();
		group2.setCode("CA321");
		group2.setId(1l);
		group2.setYear(year);
		group2.setVersion(0L);
		em.merge(group2);
	}

	@Transactional(propagation = Propagation.REQUIRED) // DEFAULT!
	public void third() {
		// al doilea user vine si face:

		// Merge + Locking
		// convertesti dintr-o structura de date a UI-ului in urmatoarea instanta de entitate:
		StudentsGroup group3 = new StudentsGroup();
		group3.setCode("421CA");
		group3.setId(1l);
		group3.setVersion(0L);
//		em.merge(group3); // this causes an exception // TODO 2013-02-04 should remove on monday
	}

}
