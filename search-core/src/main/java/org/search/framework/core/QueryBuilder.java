package org.search.framework.core;

import org.search.framework.core.dto.SearchCriterion;

public interface QueryBuilder {

    <T> Query<T> build(SearchCriterion criterias);
}
