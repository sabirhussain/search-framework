package org.search.framework.core.type;

public enum FilterCondition {
    EQUAL(1), NOT_EQUAL(2), GT(3), LT(4), EQ_GT(5), EQ_LT(6), BETWEEN(7), LIKE(8), IN(9), IS_NULL(10), IS_NOT_NULL(11);

    private Integer id;

    private FilterCondition(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
