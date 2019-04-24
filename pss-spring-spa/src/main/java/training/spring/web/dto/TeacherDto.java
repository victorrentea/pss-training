package training.spring.web.dto;

import training.spring.domain.Teacher;

public class TeacherDto {
	public Long id;
	public String name;
	
	public TeacherDto(Teacher entity) {
		id = entity.getId();
		name = entity.getName();
	}
}
