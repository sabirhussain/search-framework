package org.search.framework.core.dto;

import org.search.framework.core.type.DataType;
import org.search.framework.core.type.FilterCondition;

import java.util.Collections;
import java.util.Map;

/**
 * Placeholder for search filter.
 * 
 * @author sabir
 * 
 *         </p>
 *         {@link #suggestions} can be used to optimize filter criteria. <strong>Example:</strong> it may contain join
 *         suggestion.
 *         </p>
 *
 */
public class SearchFilter {

    private String name;

    private String value;

    private DataType dataType;

    private FilterCondition condition;

    private Map<String, String> suggestions = Collections.emptyMap();

    public String getName() {
        return name;
    }

    public SearchFilter setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public SearchFilter setValue(String value) {
        this.value = value;
        return this;
    }

    public DataType getDataType() {
        return dataType;
    }

    public SearchFilter setDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public FilterCondition getCondition() {
        return condition;
    }

    public SearchFilter setCondition(FilterCondition condition) {
        this.condition = condition;
        return this;
    }

    public Map<String, String> getSuggestions() {
        return suggestions;
    }

    public SearchFilter setSuggestions(Map<String, String> suggestions) {
        if (suggestions == null)
            return this;

        this.suggestions = suggestions;
        return this;
    }
}
