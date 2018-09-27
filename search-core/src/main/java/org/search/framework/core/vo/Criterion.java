package org.search.framework.core.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.search.framework.core.type.GroupCondition;

import java.util.Collections;
import java.util.List;

public class Criterion {

    private String entity;

    private GroupCondition groupCondition;

    private List<Filter> filters = Collections.emptyList();

    private List<Criterion> criteria = Collections.emptyList();

    @JsonCreator
    public Criterion(@JsonProperty("entity") String entity, @JsonProperty("groupCondition") GroupCondition condition,
            @JsonProperty("filters") List<Filter> filters, @JsonProperty("criteria") List<Criterion> criteria) {
        this.entity = entity;
        this.groupCondition = condition;

        if (filters != null)
            this.filters = Collections.unmodifiableList(filters);

        if (criteria != null)
            this.criteria = Collections.unmodifiableList(criteria);
    }

    public String getEntity() {
        return entity;
    }

    public GroupCondition getGroupCondition() {
        return groupCondition;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public List<Criterion> getCriteria() {
        return criteria;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((criteria == null) ? 0 : criteria.hashCode());
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        result = prime * result + ((filters == null) ? 0 : filters.hashCode());
        result = prime * result + ((groupCondition == null) ? 0 : groupCondition.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Criterion other = (Criterion) obj;
        if (criteria == null) {
            if (other.criteria != null)
                return false;
        } else if (!criteria.equals(other.criteria))
            return false;
        if (entity == null) {
            if (other.entity != null)
                return false;
        } else if (!entity.equals(other.entity))
            return false;
        if (filters == null) {
            if (other.filters != null)
                return false;
        } else if (!filters.equals(other.filters))
            return false;
        if (groupCondition != other.groupCondition)
            return false;
        return true;
    }

}
