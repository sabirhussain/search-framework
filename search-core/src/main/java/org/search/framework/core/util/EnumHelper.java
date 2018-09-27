package org.search.framework.core.util;

import org.search.framework.core.type.IdentifierType;

import java.util.Arrays;

public enum EnumHelper {

    INSTANCE;

    public <S, T extends IdentifierType<S>> T valueOfId(S id, @SuppressWarnings("unchecked") T... values) {
        if (values == null || id == null)
            return null;

        if (!values[0].getClass().isEnum()) {
            throw new IllegalArgumentException("Values provided to scan is not an Enum");
        }

        return Arrays.stream(values).filter(val -> val.getId().equals(id)).findFirst().map(item -> item).orElse(null);
    }

    public <S, T extends Enum<T>> T valueOf(S value, @SuppressWarnings("unchecked") T... values) {
        if (values == null || value == null)
            return null;

        return Arrays.stream(values).filter(val -> val.name().equals(value)).findFirst().map(item -> item).orElse(null);
    }

    /**
     * Special case of {@link String} to {@link Enum} using ignore case matching.
     * 
     * @param value the String value.
     * @param values the Enum values.
     * @return the matching Enum or null
     */
    public <T extends Enum<T>> T valueOfString(String value, @SuppressWarnings("unchecked") T... values) {
        if (values == null || value == null)
            return null;

        return Arrays.stream(values).filter(val -> val.name().equalsIgnoreCase(value)).findFirst().map(item -> item)
                .orElse(null);
    }

    public <S, T extends IdentifierType<S>> boolean isIdValid(S id, @SuppressWarnings("unchecked") T... values) {
        if (valueOfId(id, values) == null)
            return false;
        return true;
    }

    public <S, T extends Enum<T>> boolean isValueValid(S value, @SuppressWarnings("unchecked") T... values) {
        if (valueOf(value, values) == null)
            return false;
        return true;
    }

}
