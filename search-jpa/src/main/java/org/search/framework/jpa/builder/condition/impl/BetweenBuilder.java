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

public class BetweenBuilder implements ConditionBuilder {

    private final ConditionBuilderHelper builderHelper = ConditionBuilderHelper.INSTANCE;

    @Override
    public <T> Predicate build(SearchFilter filter, CriteriaBuilder criteriaBuilder, Root<T> root,
            final DistinctJoinHolder joinHolder) {
        String fieldValue = filter.getValue();
        String fieldName = filter.getName();
        Assert.notBlank("filter name and value must not be blank.", ArrayUtil.toArray(fieldName, fieldValue));

        if (fieldValue.contains(SearchConstant.FIELD_VALUE_SEPARATOR)) {
            String[] values = fieldValue.split(SearchConstant.FIELD_VALUE_SEPARATOR);
            String grpCondition = filter.getSuggestions().get(SearchConstant.SUGGESTION_KEY_GROUP_CONDITION);
            GroupCondition groupCondition = GroupCondition.valueOf(grpCondition);
            Path<Object> path = builderHelper.getPath(fieldName, root, groupCondition, joinHolder);
            DataType fieldType = filter.getDataType();

            return buildPredicate(values, fieldType, criteriaBuilder, path);
        }

        // TODO: throw exception if value separator not found.
        return null;
    }

    private Predicate buildPredicate(String[] values, DataType fieldType, CriteriaBuilder criteriaBuilder,
            Path<Object> path) {
        if (values != null && values.length > 0) {
            if (fieldType == DataType.DATETIME) {
                return getPredicateForDate(values, criteriaBuilder, path);
            } else if (fieldType == DataType.INTEGER) {
                return criteriaBuilder.between(path.as(Long.class), Long.parseLong(values[0]),
                        Long.parseLong(values[1]));
            } else if (fieldType == DataType.DECIMAL) {
                return criteriaBuilder.between(path.as(Double.class), Double.parseDouble(values[0]),
                        Double.parseDouble(values[1]));
            } else {
                return criteriaBuilder.between(path.as(String.class), values[0], values[1]);
            }
        }

        return null;
    }

    private Predicate getPredicateForDate(String[] values, CriteriaBuilder criteriaBuilder, Path<Object> path) {
        Date startDate = DateUtil.parse(values[0], SearchConstant.FIELD_VALUE_SEPARATOR, SearchConstant.DATE_PATTERNS);
        Date endDate = DateUtil.parse(values[1], SearchConstant.FIELD_VALUE_SEPARATOR, SearchConstant.DATE_PATTERNS);
        return criteriaBuilder.between(path.as(Date.class), startDate, endDate);
    }

}
