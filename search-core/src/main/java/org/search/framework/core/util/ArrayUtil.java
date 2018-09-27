package org.search.framework.core.util;

public final class ArrayUtil {

    private ArrayUtil() {
    }

    public static <T> T[] toArray(@SuppressWarnings("unchecked") T... objects) {
        return objects;
    }

}
