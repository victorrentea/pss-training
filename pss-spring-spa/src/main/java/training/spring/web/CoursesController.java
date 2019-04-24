package training.spring.web;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.spring.domain.Course;
import training.spring.repo.CourseRepository;
import training.spring.web.dto.CourseDto;

@RestController
@RequestMapping("/rest/courses")
public class CoursesController {

	@Autowired
	private CourseRepository repo;
	
	@GetMapping
	public List<CourseDto> getCourses() {
		return repo.findAll().stream().map(CourseDto::new).collect(toList());
	}
	
	@DeleteMapping("{id}")
	public void deleteCourse(@PathVariable Long id) {
		repo.deleteById(id);
	}
	
	
//	@RequestMapping(value = "courses/{id}", method = RequestMethod.GET)
	@GetMapping("{id}")
	public CourseDto getCourse(@PathVariable Long id) {
		Course entity = repo.getById(id);
		return new CourseDto(entity);
	}
	
	
}
