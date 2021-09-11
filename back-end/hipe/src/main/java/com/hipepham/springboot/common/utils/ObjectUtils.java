package com.hipepham.springboot.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * The type Object utils.
 */
public final class ObjectUtils {

    private ObjectUtils() {
    }

    /**
     * Is nullor empty boolean.
     *
     * @param object the object
     * @return the boolean
     */
    public static boolean isNullorEmpty(final Object object) {

        if (object instanceof String) {
            return object == null || ((String) object).trim().isEmpty();
        }

        if (object instanceof Collection<?>) {
            return object == null || ((Collection<?>) object).isEmpty();
        }

        if (object instanceof Map<?, ?>) {
            return object == null || ((Map<?, ?>) object).isEmpty();
        }

        return object == null;
    }
}
