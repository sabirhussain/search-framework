package org.search.framework.jpa;

public final class SearchConstant {

    private SearchConstant() {
    }

    public static final String FIELD_NAME_SEPARATOR = "\\.";
    public static final String FIELD_VALUE_SEPARATOR = ":";
    public static final String DB_FIELD_SEPERATOR = ".";
    public static final String[] DATE_PATTERNS = { "yyyy/MM/dd", "yyyy-MM-dd", "yyyyMMdd" };

    /* Filter suggestion keys */
    public static final String SUGGESTION_KEY_GROUP_CONDITION = "group_condition";
    public static final String SUGGESTION_KEY_JOIN_TYPE = "join_type";
}
