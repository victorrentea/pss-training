package ro.victor.training.jpa2.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

public class LabActivity extends TeachingActivity {

	private StudentsGroup group;

	public StudentsGroup getGroup() {
		return group;
	}

	public void setGroup(StudentsGroup group) {
		this.group = group;
	}

}
