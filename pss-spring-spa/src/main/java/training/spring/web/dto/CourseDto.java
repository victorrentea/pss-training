package training.spring.web.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import training.spring.domain.Course;
import training.spring.domain.Teacher;

public class CourseDto {
	public Long id; 
	public String name;
	public String description;
	public String startDate;
	public long teacherId;
	public String teacherName;
	
	public CourseDto(Course entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		startDate = new SimpleDateFormat("yyyy-MM-dd").format(entity.getStartDate());
		teacherId = entity.getTeacher().getId();
		teacherName = entity.getTeacher().getName();
	}
	
}
