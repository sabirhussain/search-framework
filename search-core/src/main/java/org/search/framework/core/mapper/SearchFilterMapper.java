package org.search.framework.core.mapper;

import org.search.framework.core.dto.SearchFilter;
import org.search.framework.core.vo.Filter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface SearchFilterMapper {

    default SearchFilter toSearchFilter(Filter filter) {
        if (filter == null)
            return null;

        SearchFilter searchFilter = new SearchFilter().setName(filter.getName()).setValue(filter.getValue())
                .setCondition(filter.getCondition());
        return searchFilter;
    }

    default List<SearchFilter> toSearchFilters(List<Filter> filters) {
        if (filters == null || filters.isEmpty())
            return Collections.emptyList();

        return filters.stream().map(this::toSearchFilter).collect(Collectors.toList());
    }
}
