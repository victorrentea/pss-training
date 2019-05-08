package ro.victor.training.jpa2.repo;

public class TeacherAndCourseDto {
	private String teacherName;
	private String courseName;
	public TeacherAndCourseDto() {
	}
	public TeacherAndCourseDto(String teacherName, String courseName) {
		this.teacherName = teacherName;
		this.courseName = courseName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String toString() {
		return "TeacherAndCourseDto [teacherName=" + teacherName + ", courseName=" + courseName + "]";
	}
	
	
	
}
