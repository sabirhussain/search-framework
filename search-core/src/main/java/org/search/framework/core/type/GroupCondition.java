package org.search.framework.core.type;

public enum GroupCondition {

    AND(1), OR(2);

    private Integer id;

    private GroupCondition(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
