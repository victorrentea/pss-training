package ro.victor.training.jpa2.domain.entity;

import static java.util.Collections.unmodifiableSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

@Entity
public class Teacher {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Grade grade;
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private TeacherDetails details;
	
	@ElementCollection
//	@OrderColumn
	@OrderBy("value ASC, type ASC")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher", cascade=CascadeType.PERSIST)
	private Set<Subject> heldSubjects = new HashSet<>() ;
	
	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();
	
	@Embedded
	@AttributeOverrides({
	     @AttributeOverride(name="startHour", column=@Column(name="COUNSELING_START_HOUR"))
	})
	private TimeSlot counselingTimeSlot = new TimeSlot();
	
	public Teacher() {
		
	}
	
	
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + "]";
	}


	public Teacher(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TeacherDetails getDetails() {
		return details;
	}

	public void setDetails(TeacherDetails details) {
		this.details = details;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Set<TeachingActivity> getActivities() {
		return activities;
	}

	public void setActivities(Set<TeachingActivity> activities) {
		this.activities = activities;
	}

	

	public TimeSlot getCounselingTimeSlot() {
		return counselingTimeSlot;
	}

	public void setCounselingTimeSlot(TimeSlot counselingTimeSlot) {
		this.counselingTimeSlot = counselingTimeSlot;
	}

	public List<ContactChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<ContactChannel> channel) {
		this.channels = channel;
	}

	public Set<Subject> getHeldSubjects() {
//		return unmodifiableSet(heldSubjects);
		return heldSubjects;
	}

	public void addSubject(Subject subject) {
		heldSubjects.add(subject);
		subject.setHolderTeacher(this);
	}
	public void removeSubject(Subject subject) {
		 heldSubjects.remove(subject);
		 subject.setHolderTeacher(null);
	}
	
	public void setHeldSubjects(Set<Subject> heldSubjects) {
		new HashSet<>(this.heldSubjects).forEach(this::removeSubject);
		heldSubjects.forEach(this::addSubject);
	}

}
