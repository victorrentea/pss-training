package training.spring.web;

import static java.util.stream.Collectors.toList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.spring.domain.Course;
import training.spring.repo.CourseRepository;
import training.spring.repo.TeacherRepository;
import training.spring.web.dto.CourseDto;

@RestController
@RequestMapping("/rest/courses")
public class CoursesController {

	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@GetMapping
	public List<CourseDto> getCourses() {
		return courseRepo.findAll().stream().map(CourseDto::new).collect(toList());
	}
	
	@DeleteMapping("{id}")
	public void deleteCourse(@PathVariable Long id) {
		courseRepo.deleteById(id);
	}
	
	
//	@RequestMapping(value = "courses/{id}", method = RequestMethod.GET)
	@GetMapping("{id}")
	public CourseDto getCourse(@PathVariable Long id) {
		Course entity = courseRepo.getById(id);
		return new CourseDto(entity);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> updateCourse(@PathVariable Long id, 
			@RequestBody CourseDto updatedCourse) {
		Course course = courseRepo.getById(id);
		course.setName(updatedCourse.name);
		course.setTeacher(teacherRepo.getById(updatedCourse.teacherId));
		course.setDescription(updatedCourse.description);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(CourseDto.START_DATE_PATTERN);
			sdf.setLenient(false);
			Date startDate = sdf.parse(updatedCourse.startDate);
			course.setStartDate(startDate);
		} catch (ParseException exception) {
			return ResponseEntity.badRequest().body("ceva");
		}
		return ResponseEntity.ok().build();
	}
	
	
}
