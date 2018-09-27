package org.search.framework.core.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public final class DateUtil {

    private DateUtil() {

    }

    /**
     * Try to parse simple date value or date value with embedded pattern or with suggested patterns.
     * 
     * @param value the value to parse
     * @param delimiter the delimiter used to separate value with pattern if pattern embedded in value.
     * @param patterns the patterns to try if no embedded pattern found.
     * @return the parsed {@link Date}.
     * @throws RuntimeException if parsing fail.
     */
    public static Date parse(String value, String delimiter, String[] patterns) {
        String pattern = null;

        if (value.contains(delimiter)) {
            String[] values = value.split(delimiter);
            value = values[0];
            pattern = values[1];
        }

        try {
            if (pattern != null) {
                return DateUtils.parseDate(value, pattern);
            }

            return DateUtils.parseDate(value, patterns);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
