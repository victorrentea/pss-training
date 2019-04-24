package training.spring.pssspringspa;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import training.spring.web.CoursesController;
import training.spring.web.dto.CourseDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PssSpringSpaApplicationTests {
	@Autowired
	private CoursesController coursesController;
	

	@Test(expected = IllegalArgumentException.class)
	public void contextLoads() throws ParseException {
		CourseDto dto = new CourseDto();
		dto.name = "curs";
		dto.description = null;
		coursesController.createCourse(dto);
	}
	
	@Test
	public void saves() throws ParseException {
		CourseDto dto = new CourseDto();
		dto.name = "curs";
		dto.description = "desc";
		dto.teacherId = 13L;
		dto.startDate = "2019-01-02";
		coursesController.createCourse(dto);
		
	}

}
