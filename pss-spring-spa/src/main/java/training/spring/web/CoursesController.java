package training.spring.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.spring.domain.Course;
import training.spring.repo.CourseRepository;

@RestController
public class CoursesController {

	@Autowired
	private CourseRepository repo;
	
	@RequestMapping("/rest/courses")
	public List<Course> getCourses() {
		return null;
	}
}
