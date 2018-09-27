package org.search.framework.jpa.builder.condition;

import org.search.framework.core.dto.SearchFilter;
import org.search.framework.jpa.helper.DistinctJoinHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface ConditionBuilder {

    <T> Predicate build(SearchFilter filter, CriteriaBuilder criteriaBuilder, Root<T> root,
            final DistinctJoinHolder joinHolder);
}
