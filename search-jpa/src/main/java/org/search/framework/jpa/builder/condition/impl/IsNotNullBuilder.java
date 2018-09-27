package org.search.framework.jpa.builder.condition.impl;

import org.search.framework.core.dto.SearchFilter;
import org.search.framework.core.type.GroupCondition;
import org.search.framework.jpa.SearchConstant;
import org.search.framework.jpa.builder.condition.ConditionBuilder;
import org.search.framework.jpa.helper.ConditionBuilderHelper;
import org.search.framework.jpa.helper.DistinctJoinHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class IsNotNullBuilder implements ConditionBuilder {

    private final ConditionBuilderHelper builderHelper = ConditionBuilderHelper.INSTANCE;

    @Override
    public <T> Predicate build(SearchFilter filter, CriteriaBuilder criteriaBuilder, Root<T> root,
            final DistinctJoinHolder joinHolder) {
        String fieldName = filter.getName();
        String grpCondition = filter.getSuggestions().get(SearchConstant.SUGGESTION_KEY_GROUP_CONDITION);
        GroupCondition groupCondition = GroupCondition.valueOf(grpCondition);
        Path<Object> path = builderHelper.getPath(fieldName, root, groupCondition, joinHolder);
        return criteriaBuilder.isNotNull(path);
    }

}
