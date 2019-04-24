package training.spring.web;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.spring.repo.TeacherRepository;
import training.spring.web.dto.TeacherDto;

@RestController
@RequestMapping("/rest/teachers")
public class TeachersController {
	private TeacherRepository repo;

	public TeachersController(TeacherRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public List<TeacherDto> getTeachers() { 
		return repo.findAll().stream().map(TeacherDto::new).collect(toList());
	}
}
