package org.search.framework.core.mapper;

import org.search.framework.core.dto.SearchCriterion;
import org.search.framework.core.vo.Criterion;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface SearchCriterionMapper {

    default SearchCriterion toSearchCriterion(Criterion criterion) {
        if (criterion == null)
            return null;

        SearchFilterMapper filterMapper = new SearchFilterMapper() {
        };

        SearchCriterion searchCriteria = new SearchCriterion().setEntity(criterion.getEntity())
                .setGroupCondition(criterion.getGroupCondition())
                .setFilters(filterMapper.toSearchFilters(criterion.getFilters()))
                .setCriteria(toSearchCriteria(criterion.getCriteria()));
        return searchCriteria;
    }

    default List<SearchCriterion> toSearchCriteria(List<Criterion> criteria) {

        if (criteria == null || criteria.isEmpty())
            return Collections.emptyList();

        return criteria.stream().map(this::toSearchCriterion).collect(Collectors.toList());
    }
}
