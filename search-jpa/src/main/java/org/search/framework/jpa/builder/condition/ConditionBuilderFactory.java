package org.search.framework.jpa.builder.condition;

import org.search.framework.core.type.FilterCondition;
import org.search.framework.jpa.builder.condition.impl.BetweenBuilder;
import org.search.framework.jpa.builder.condition.impl.EqualBuilder;
import org.search.framework.jpa.builder.condition.impl.GreaterThanBuilder;
import org.search.framework.jpa.builder.condition.impl.GreaterThanEqualBuilder;
import org.search.framework.jpa.builder.condition.impl.InBuilder;
import org.search.framework.jpa.builder.condition.impl.IsNotNullBuilder;
import org.search.framework.jpa.builder.condition.impl.IsNullBuilder;
import org.search.framework.jpa.builder.condition.impl.LessThanBuilder;
import org.search.framework.jpa.builder.condition.impl.LessThanEqualBuilder;
import org.search.framework.jpa.builder.condition.impl.LikeBuilder;
import org.search.framework.jpa.builder.condition.impl.NotEqualBuilder;

import java.util.HashMap;
import java.util.Map;

public enum ConditionBuilderFactory {
    INSTANCE;

    private Map<FilterCondition, ConditionBuilder> builders;

    private ConditionBuilderFactory() {
        builders = new HashMap<FilterCondition, ConditionBuilder>();
        builders.put(FilterCondition.EQUAL, new EqualBuilder());
        builders.put(FilterCondition.NOT_EQUAL, new NotEqualBuilder());
        builders.put(FilterCondition.GT, new GreaterThanBuilder());
        builders.put(FilterCondition.LT, new LessThanBuilder());
        builders.put(FilterCondition.EQ_GT, new GreaterThanEqualBuilder());
        builders.put(FilterCondition.EQ_LT, new LessThanEqualBuilder());
        builders.put(FilterCondition.BETWEEN, new BetweenBuilder());
        builders.put(FilterCondition.LIKE, new LikeBuilder());
        builders.put(FilterCondition.IN, new InBuilder());
        builders.put(FilterCondition.IS_NULL, new IsNullBuilder());
        builders.put(FilterCondition.IS_NOT_NULL, new IsNotNullBuilder());
    }

    public void register(FilterCondition condition, ConditionBuilder builder) {
        if (condition == null || builder == null)
            throw new IllegalArgumentException("condition and builder must not be null.");

        builders.put(condition, builder);
    }

    public ConditionBuilder getBuilder(FilterCondition filterCondition) {
        return builders.get(filterCondition);
    }
}
