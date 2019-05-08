package ro.victor.training.jpa2.repo;

public class TeacherCriteria {
	private String namePart;

	public TeacherCriteria(String namePart) {
		this.namePart = namePart;
	}

	public String getNamePart() {
		return namePart;
	}

	public void setNamePart(String namePart) {
		this.namePart = namePart;
	}
	
	public TeacherCriteria() {
	}
}
