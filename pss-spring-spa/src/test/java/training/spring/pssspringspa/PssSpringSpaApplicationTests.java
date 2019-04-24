package training.spring.pssspringspa;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import training.spring.domain.Course;
import training.spring.domain.Teacher;
import training.spring.repo.CourseRepository;
import training.spring.repo.TeacherRepository;
import training.spring.web.CoursesController;
import training.spring.web.dto.CourseDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PssSpringSpaApplicationTests {
	
	@Autowired
	private CoursesController controller;
	
	@MockBean
	private TeacherRepository teacherRepo;
	@MockBean
	private CourseRepository courseRepo;
	
	@Test(expected = IllegalArgumentException.class)
	public void createCourseFailsForMissingDescription() throws ParseException {
		when(teacherRepo.getById(13L)).thenReturn(new Teacher());
		CourseDto dto = new CourseDto();
		dto.teacherId = 13L;
		controller.createCourse(dto);
	}
	
	@Test
	public void createCourse() throws ParseException {
		when(teacherRepo.getById(13L)).thenReturn(new Teacher());
		CourseDto dto = new CourseDto();
		dto.teacherId = 13L;
		dto.description="d";
		dto.startDate="2019-01-01";
		controller.createCourse(dto);
		
		verify(courseRepo).save(ArgumentMatchers.any());
	}
	@Test(expected = ParseException.class)
	public void createCourseFailsForDateforma() throws ParseException {
		when(teacherRepo.getById(13L)).thenReturn(new Teacher());
		CourseDto dto = new CourseDto();
		dto.teacherId = 13L;
		dto.description="d";
		dto.startDate="2019-01321";
		controller.createCourse(dto);
	}


}
