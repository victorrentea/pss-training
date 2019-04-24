package training.spring.web;

import static java.util.stream.Collectors.toList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public void updateCourse(@PathVariable Long id, 
			@RequestBody CourseDto dto) throws ParseException {
		Course entity = courseRepo.getById(id);
		fill(dto, entity);
	}
	
	@PostMapping
	public void createCourse(@RequestBody CourseDto dto) throws ParseException {
		Course entity = new Course();
		fill(dto, entity);
		courseRepo.save(entity);
	}
	
	private void fill(CourseDto sourceDto, Course targetEntity) throws ParseException {
		targetEntity.setName(sourceDto.name);
		targetEntity.setTeacher(teacherRepo.getById(sourceDto.teacherId));
		if (StringUtils.isBlank(sourceDto.description)) {
			throw new IllegalArgumentException("Course description is a mandatory field that should have been validated in the UI, you dirty lttle hacker !!!");
		}
		targetEntity.setDescription(sourceDto.description);
		SimpleDateFormat sdf = new SimpleDateFormat(CourseDto.START_DATE_PATTERN);
		sdf.setLenient(false);
		Date startDate = sdf.parse(sourceDto.startDate);
		targetEntity.setStartDate(startDate);
	}
	
}
