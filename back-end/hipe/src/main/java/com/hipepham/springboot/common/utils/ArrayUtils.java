package com.hipepham.springboot.common.utils;

/**
 * The type Array utils.
 */
public final class ArrayUtils {

    private ArrayUtils() {
    }


    /**
     * Whether array is blank.
     *
     * @param <T> the type parameter
     * @param arr the arr
     * @return the boolean
     */
    public static <T> boolean isBlank(final T[] arr) {
        if (arr == null || arr.length == 0) {
            return true;
        }
        return false;
    }
}
