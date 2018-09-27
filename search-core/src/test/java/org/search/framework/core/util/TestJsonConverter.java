package org.search.framework.core.util;

import org.junit.Assert;
import org.junit.Test;
import org.search.framework.core.type.FilterCondition;
import org.search.framework.core.type.GroupCondition;
import org.search.framework.core.util.JsonConverter;
import org.search.framework.core.vo.Criterion;
import org.search.framework.core.vo.Filter;

import java.util.Arrays;
import java.util.Optional;

public class TestJsonConverter {

    @Test
    public void shouldParseFilter() {
        Filter filter = new Filter("first_name", "Sabir", FilterCondition.EQUAL);
        Optional<String> json = JsonConverter.INSTANCE.getJson(filter);
        Assert.assertNotNull(json.map(j -> j).orElse(null));

        json.ifPresent(data -> {
            Optional<Filter> parsedFilter = JsonConverter.INSTANCE.getObject(data, Filter.class);
            Assert.assertNotNull(parsedFilter.map(f -> f).orElse(null));
            parsedFilter.ifPresent(d -> Assert.assertEquals(filter, d));
        });
    }

    @Test
    public void shouldParseCriteria() {
        Filter filter = new Filter("first_name", "Sabir", FilterCondition.EQUAL);
        Criterion criterion_a = new Criterion("Sample", GroupCondition.OR, Arrays.asList(filter), null);
        Criterion criterion_b = new Criterion("Sample", GroupCondition.OR, Arrays.asList(filter), null);
        Criterion criteria = new Criterion("Sample", GroupCondition.AND, Arrays.asList(filter),
                Arrays.asList(criterion_a, criterion_b));
        Optional<String> json = JsonConverter.INSTANCE.getJson(criteria);
        Assert.assertNotNull(json.map(j -> j).orElse(null));

        json.ifPresent(data -> {
            Optional<Criterion> parsedCriteria = JsonConverter.INSTANCE.getObject(data, Criterion.class);
            Assert.assertNotNull(parsedCriteria.map(f -> f).orElse(null));
            parsedCriteria.ifPresent(d -> Assert.assertEquals(criteria, d));
        });
    }
}
