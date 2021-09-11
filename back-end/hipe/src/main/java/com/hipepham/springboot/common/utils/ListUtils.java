package com.hipepham.springboot.common.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type List utils.
 */
public final class ListUtils {

    private ListUtils() {
    }

    /**
     * Whether list is blank.
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return the boolean
     */
    public static <T> boolean isBlank(final List list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }
    
    /**
     * Concat 2 list with remove item diferent.
     *
     * @param lstId the lst id
     * @param ip    the ip
     * @return list list
     */
    public static List<Long> combineList(List<Long> lstId,
										   List<Long> ip) {
        if (lstId == null || ip == null) {
            return null;
        }
        List<Long> res = lstId.stream().filter(x -> ip.contains(x))
                .collect(Collectors.toList());
        return res;
    }
}
