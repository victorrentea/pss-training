package ro.victor.training.jpa2.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ro.victor.training.jpa2.domain.entity.Teacher;
import ro.victor.training.jpa2.domain.entity.TeachingActivity;
import ro.victor.training.jpa2.facade.dto.ActivitySearchCriteria;

public class TeacherRepositoryImpl implements TeacherRespositoryCustom {

	@Autowired
	private EntityManager em;

	@Override
	public List<TeachingActivity> searchActivity(ActivitySearchCriteria criteria) {
		Map<String, Object> params = new HashMap<>();
		String jpql = "SELECT a FROM TeachingActivity a WHERE 1=1 ";
		
		if (StringUtils.isNotBlank(criteria.subject)) {
			jpql += " AND UPPER(a.subject.name) LIKE UPPER('%' || :subject || '%') ";
			params.put("subject", criteria.subject);
		}
		
		if (StringUtils.isNotBlank(criteria.roomId)) {
			jpql += " AND UPPER(a.timeSlot.roomId) = :roomId";
			params.put("roomId", criteria.roomId);
		}

		if (criteria.day != null) {
			jpql += " AND a.day = :day";
			params.put("day", criteria.day);
		}
		
		TypedQuery<TeachingActivity> query = em.createQuery(jpql, TeachingActivity.class);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		return query.getResultList();
	}

}
