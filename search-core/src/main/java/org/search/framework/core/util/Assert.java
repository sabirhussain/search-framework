package org.search.framework.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public final class Assert {

    private Assert() {

    }

    public static void notNull(Object... objects) {
        notNull("[Assertion failed] - argument(s) must not be null.", objects);
    }

    public static void notNull(String message, Object... objects) {
        Arrays.stream(objects).forEach(object -> notNull(object, message));
    }

    public static void notBlank(String[] texts) {
        notBlank("[Assertion failed] - string argument(s) must not be blank.", texts);
    }

    public static void notBlank(String message, String[] texts) {
        Arrays.stream(texts).forEach(text -> notBlank(message, text));
    }

    public static void notBlank(String message, String text) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
