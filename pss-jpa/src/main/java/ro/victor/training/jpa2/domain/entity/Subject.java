package ro.victor.training.jpa2.domain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Subject {
	@Id
	private Long id;
	
	private String name;
	
	private boolean active;
	
	@ManyToOne
	private Teacher holderTeacher;
	
	@OneToMany(mappedBy = "subject")
	private List<TeachingActivity> activities = new ArrayList<>();
	
	private LocalDate lastModifiedDate;
	
	private String lastModifiedBy;

	
//	@PrePersist
//	@PreUpdate
//	public void automaticUpdateTrackingColumns() {
//		System.out.println("Before persist/update Subject");
//		lastModifiedDate = LocalDateTime.now();
//		lastModifiedBy = MyUtil.getUserOnCurrentThread();
//	}
	
	
	
	public Subject() {
	}
	
	
	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Subject(String name) {
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

	public Teacher getHolderTeacher() {
		return holderTeacher;
	}

	public void setHolderTeacher(Teacher holder) {
		this.holderTeacher = holder;
	}

	public List<TeachingActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<TeachingActivity> activities) {
		this.activities = activities;
	}
	
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public LocalDate getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public void setLastModifiedDate(LocalDate lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
}
