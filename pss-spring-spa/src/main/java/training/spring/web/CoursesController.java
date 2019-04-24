package training.spring.web;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.spring.repo.CourseRepository;
import training.spring.web.dto.CourseDto;

@RestController
public class CoursesController {

	@Autowired
	private CourseRepository repo;
	
	@RequestMapping("/rest/courses")
	public List<CourseDto> getCourses() {
		return repo.findAll().stream()
				.map(e -> new CourseDto(e))
				.collect(toList());
	}
}
