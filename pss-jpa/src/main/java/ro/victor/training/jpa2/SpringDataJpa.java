package ro.victor.training.jpa2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.victor.training.jpa2.domain.entity.Teacher;
import ro.victor.training.jpa2.domain.entity.TeachingActivity;
import ro.victor.training.jpa2.facade.dto.ActivitySearchCriteria;
import ro.victor.training.jpa2.repo.TeacherAndCourseDto;
import ro.victor.training.jpa2.repo.TeacherRepository;

@Service
public class SpringDataJpa {
	@Autowired
	private TeacherRepository teacherRepo;

	@Transactional
	public void play() {
		System.out.println("Enter the SPring Data JPA world: " + teacherRepo.getClass());
		 Teacher teacher = teacherRepo.findOne(1L);
		 System.out.println(teacher);
		 
		 System.out.println("by name: " + teacherRepo.findByName("Tavi"));
		 Teacher tt = teacherRepo.findByDetailsCv("cv2");
		System.out.println("by cv: " + tt);
		System.out.println("tt.class:"  + tt.getClass() );
		tt.getDetails();
		System.out.println("details.class:"  + tt.getDetails().getClass() );
		tt.getDetails().getCv();
		
		
		List<TeacherAndCourseDto> list = teacherRepo.findAllTeacherNameAndCourseNamePairs();
		System.out.println("perechi: " + list);
		
		
		ActivitySearchCriteria criteria = new ActivitySearchCriteria();
		criteria.roomId = "AULA";
		List<TeachingActivity> activity = teacherRepo.searchActivity(criteria);
	}

}
