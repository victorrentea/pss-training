package training.spring.domain;

public abstract class BaseEntity {
	protected Long id;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}
	
}
