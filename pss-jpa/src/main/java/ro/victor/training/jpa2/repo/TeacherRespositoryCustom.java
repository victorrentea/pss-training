package ro.victor.training.jpa2.repo;

import java.util.List;

import ro.victor.training.jpa2.domain.entity.Teacher;
import ro.victor.training.jpa2.domain.entity.TeachingActivity;
import ro.victor.training.jpa2.facade.dto.ActivitySearchCriteria;

public interface TeacherRespositoryCustom {

	List<TeachingActivity> searchActivity(ActivitySearchCriteria criteria);

}
