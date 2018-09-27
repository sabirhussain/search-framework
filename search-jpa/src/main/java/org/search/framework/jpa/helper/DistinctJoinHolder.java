package org.search.framework.jpa.helper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.criteria.Join;

public class DistinctJoinHolder {

    private final Map<String, Join<Object, Object>> pathToJoin = new ConcurrentHashMap<>();

    public Join<Object, Object> getJoin(String key) {
        return pathToJoin.get(key);
    }

    /**
     * To put {@link Join} into collection. This will not override existing entry and will reject silently.
     * 
     * @param key the key to identify join.
     * @param join the {@link Join} object.
     */
    public void putJoin(String key, Join<Object, Object> join) {
        if (pathToJoin.get(key) == null) {
            pathToJoin.put(key, join);
        }
    }
}
