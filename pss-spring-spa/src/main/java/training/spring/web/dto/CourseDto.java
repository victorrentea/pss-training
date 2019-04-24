package training.spring.web.dto;

import java.util.Date;

import training.spring.domain.Course;
import training.spring.domain.Teacher;

public class CourseDto {
	public String name;
	public String description;
	public Date startDate;
	public long teacherId;
	
	public CourseDto(Course entity) {
		name = entity.getName();
		description = entity.getDescription();
		startDate = entity.getStartDate();
		teacherId = entity.getTeacher().getId();
	}
	
}
