package ro.victor.training.jpa2.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import ro.victor.training.jpa2.common.data.EntityRepository;
import ro.victor.training.jpa2.domain.entity.CourseActivity;
import ro.victor.training.jpa2.domain.entity.Teacher;

public interface TeacherRepository extends EntityRepository<Teacher, Long>, TeacherRespositoryCustom{

	Teacher findByName(String string);
	
	Teacher findByDetailsCv(String cv);
	
	@Query("SELECT c FROM CourseActivity c JOIN c.teachers t WHERE t.name = ?1")
	List<CourseActivity> findByTeacherName(String teacherName);

	@Query("SELECT new ro.victor.training.jpa2.repo.TeacherAndCourseDto(t.name,c.timeSlot.roomId) FROM Teacher t JOIN t.activities c")
	List<TeacherAndCourseDto> findAllTeacherNameAndCourseNamePairs();
	
}
