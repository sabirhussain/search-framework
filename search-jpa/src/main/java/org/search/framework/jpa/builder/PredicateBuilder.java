package org.search.framework.jpa.builder;

import org.apache.commons.lang3.StringUtils;
import org.search.framework.core.dto.SearchCriterion;
import org.search.framework.core.dto.SearchFilter;
import org.search.framework.core.type.GroupCondition;
import org.search.framework.core.util.EnumHelper;
import org.search.framework.jpa.SearchConstant;
import org.search.framework.jpa.builder.condition.ConditionBuilder;
import org.search.framework.jpa.builder.condition.ConditionBuilderFactory;
import org.search.framework.jpa.helper.DistinctJoinHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public enum PredicateBuilder {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(PredicateBuilder.class);

    private ConditionBuilderFactory builderFactory = ConditionBuilderFactory.INSTANCE;

    public <T> Predicate build(SearchCriterion criterion, CriteriaBuilder cb, Root<T> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (criterion.getCriteria() != null && !criterion.getCriteria().isEmpty()) {
            predicates
                    .addAll(criterion.getCriteria().stream().map(c -> build(c, cb, root)).collect(Collectors.toList()));
        }

        final DistinctJoinHolder joinHolder = new DistinctJoinHolder();

        if (criterion.getFilters() != null && !criterion.getFilters().isEmpty())
            criterion.getFilters().forEach(filter -> {
                JoinType joinType = EnumHelper.INSTANCE.valueOfString(
                        filter.getSuggestions().get(SearchConstant.SUGGESTION_KEY_JOIN_TYPE), JoinType.values());
                applyJoinSuggestion(filter.getName(), joinType, root, joinHolder);
                Predicate filterPredicate = buildPredicate(filter, cb, root, joinHolder);

                if (filterPredicate != null) {
                    predicates.add(filterPredicate);
                    return;
                }

                LOG.error("Error Occurred!!!.Predicate is null. Please check the input");
            });

        if (criterion.getGroupCondition() == GroupCondition.AND) {
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }

        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    private <T> void applyJoinSuggestion(String fieldName, JoinType joinType, Root<T> root,
            final DistinctJoinHolder joinHolder) {
        if (null != joinType && joinHolder.getJoin(fieldName) == null) {
            Join<Object, Object> join = root.join(fieldName, joinType);
            joinHolder.putJoin(fieldName, join);
        }
    }

    private <T> Predicate buildCondition(SearchFilter filter, CriteriaBuilder cb, Root<T> root,
            final DistinctJoinHolder joinHolder) {
        if (filter.getCondition() == null && StringUtils.isAnyBlank(filter.getName(), filter.getValue())) {
            throw new IllegalArgumentException("[condition, fieldName, fieldValue] are mandatory.");
        }

        ConditionBuilder conditionBuilder = builderFactory.getBuilder(filter.getCondition());
        return conditionBuilder.build(filter, cb, root, joinHolder);
    }

    private <T> Predicate buildPredicate(SearchFilter searchFilter, CriteriaBuilder cb, Root<T> root,
            final DistinctJoinHolder joinHolder) {
        Predicate filterPredicate = null;

        filterPredicate = buildCondition(searchFilter, cb, root, joinHolder);

        return filterPredicate;
    }
}
