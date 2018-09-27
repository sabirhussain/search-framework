package org.search.framework.core.dto;

import org.search.framework.core.type.GroupCondition;

import java.util.List;

public class SearchCriterion {
    private String entity;

    private GroupCondition groupCondition;

    private List<SearchFilter> filters;

    private List<SearchCriterion> criteria;

    public String getEntity() {
        return entity;
    }

    public SearchCriterion setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public GroupCondition getGroupCondition() {
        return groupCondition;
    }

    public SearchCriterion setGroupCondition(GroupCondition groupCondition) {
        this.groupCondition = groupCondition;
        return this;
    }

    public List<SearchFilter> getFilters() {
        return filters;
    }

    public SearchCriterion setFilters(List<SearchFilter> filters) {
        this.filters = filters;
        return this;
    }

    public List<SearchCriterion> getCriteria() {
        return criteria;
    }

    public SearchCriterion setCriteria(List<SearchCriterion> criteria) {
        this.criteria = criteria;
        return this;
    }

}
