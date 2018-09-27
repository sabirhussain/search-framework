package org.search.framework.jpa.helper;

import org.search.framework.core.type.DataType;
import org.search.framework.core.type.GroupCondition;
import org.search.framework.core.util.DateUtil;
import org.search.framework.jpa.SearchConstant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public enum ConditionBuilderHelper {
    INSTANCE;

    public <T> Path<Object> getPath(String fieldName, JoinType joinType, Root<T> root, GroupCondition groupCondition,
            final DistinctJoinHolder joinHolder) {
        Path<Object> pathParam = null;

        if (fieldName.contains(SearchConstant.DB_FIELD_SEPERATOR)) {
            String[] values = fieldName.split(SearchConstant.FIELD_NAME_SEPARATOR);
            pathParam = buildComplexPath(values, joinType, root, groupCondition, joinHolder);
        } else {
            pathParam = root.get(fieldName);
        }

        return pathParam;
    }

    public <T> Path<Object> getPath(String fieldName, Root<T> root, GroupCondition groupCondition,
            final DistinctJoinHolder joinHolder) {
        return getPath(fieldName, null, root, groupCondition, joinHolder);
    }

    public Number convertToNum(DataType fieldType, String fieldValue) {
        if (fieldType == DataType.INTEGER) {
            return Long.parseLong(fieldValue);
        } else if (fieldType == DataType.DECIMAL) {
            return Double.parseDouble(fieldValue);
        }

        return null;
    }

    public List<Object> convertValues(DataType fieldType, String... fieldValues) {
        List<Object> values = new ArrayList<Object>();

        for (String value : fieldValues) {
            values.add(convertValue(fieldType, value));
        }

        return values;
    }

    public Object convertValue(DataType fieldType, String fieldValue) {
        if (DataType.DATETIME == fieldType) {
            return DateUtil.parse(fieldValue, SearchConstant.FIELD_VALUE_SEPARATOR, SearchConstant.DATE_PATTERNS);
        } else if (fieldType == DataType.INTEGER || fieldType == DataType.DECIMAL) {
            return convertToNum(fieldType, fieldValue);
        } else if (fieldType == DataType.BOOLEAN) {
            return Boolean.parseBoolean(fieldValue);
        }

        return fieldValue;
    }

    private <T> Path<Object> buildComplexPath(String[] value, JoinType joinType, Root<T> root,
            GroupCondition groupCondition, final DistinctJoinHolder joinHolder) {
        StringBuilder joinKey = new StringBuilder();
        Path<Object> complexPath = null;
        Join<Object, Object> join = null;
        JoinType groupJoinType = joinType;
        if (joinType == null && GroupCondition.OR == groupCondition) {
            groupJoinType = JoinType.LEFT;
        } else if (joinType == null) {
            groupJoinType = JoinType.INNER;
        }

        for (int i = 0; i < value.length; i++) {
            appendToJoinKey(joinKey, value[i]);

            if (join == null) {
                // Prepare parent join
                join = prepareJoin(null, joinKey, value[i], joinHolder, root, groupJoinType);
            } else {
                if (i < value.length - 1) {
                    join = prepareJoin((Join<Object, Object>) join, joinKey, value[i], joinHolder, root, groupJoinType);
                } else {
                    complexPath = join.get(value[i]);
                }
            }
        }

        return complexPath;
    }

    private void appendToJoinKey(StringBuilder joinKey, String path) {
        if (joinKey.length() == 0) {
            joinKey.append(path);
        } else {
            joinKey.append('.').append(path);
        }
    }

    private <T> Join<Object, Object> prepareJoin(Join<Object, Object> parentJoin, StringBuilder joinKey,
            String fieldName, final DistinctJoinHolder joinHolder, Root<T> root, JoinType joinType) {
        String key = joinKey.toString();
        // Get previously prepared join.
        Join<Object, Object> join = joinHolder.getJoin(key);

        if (join == null) {
            if (parentJoin == null) {
                join = root.join(fieldName, joinType);
            } else {
                join = parentJoin.join(fieldName, joinType);
            }
            joinHolder.putJoin(key, join);
        }

        return join;
    }
}
