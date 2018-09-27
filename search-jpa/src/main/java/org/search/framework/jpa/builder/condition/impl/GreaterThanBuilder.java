package org.search.framework.jpa.builder.condition.impl;

import org.search.framework.core.dto.SearchFilter;
import org.search.framework.core.type.DataType;
import org.search.framework.core.type.GroupCondition;
import org.search.framework.core.util.ArrayUtil;
import org.search.framework.core.util.Assert;
import org.search.framework.core.util.DateUtil;
import org.search.framework.jpa.SearchConstant;
import org.search.framework.jpa.builder.condition.ConditionBuilder;
import org.search.framework.jpa.helper.ConditionBuilderHelper;
import org.search.framework.jpa.helper.DistinctJoinHolder;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GreaterThanBuilder implements ConditionBuilder {

    private final ConditionBuilderHelper builderHelper = ConditionBuilderHelper.INSTANCE;

    @Override
    public <T> Predicate build(SearchFilter filter, CriteriaBuilder criteriaBuilder, Root<T> root,
            final DistinctJoinHolder joinHolder) {
        String fieldValue = filter.getValue();
        String fieldName = filter.getName();
        Assert.notBlank("filter name and value must not be blank.", ArrayUtil.toArray(fieldName, fieldValue));
        DataType fieldType = filter.getDataType();
        String grpCondition = filter.getSuggestions().get(SearchConstant.SUGGESTION_KEY_GROUP_CONDITION);
        GroupCondition groupCondition = GroupCondition.valueOf(grpCondition);

        Path<Object> path = builderHelper.getPath(fieldName, root, groupCondition, joinHolder);

        if (fieldType == DataType.INTEGER || fieldType == DataType.DECIMAL) {
            return criteriaBuilder.gt(path.as(Number.class), builderHelper.convertToNum(fieldType, fieldValue));
        } else if (fieldType == DataType.DATETIME) {
            return getPredicateForDate(fieldValue, criteriaBuilder, path);
        }

        return null;
    }

    private Predicate getPredicateForDate(String fieldValue, CriteriaBuilder criteriaBuilder, Path<Object> path) {
        Date date = DateUtil.parse(fieldValue, SearchConstant.FIELD_VALUE_SEPARATOR, SearchConstant.DATE_PATTERNS);
        return criteriaBuilder.greaterThan(path.as(Date.class), date);
    }

}
