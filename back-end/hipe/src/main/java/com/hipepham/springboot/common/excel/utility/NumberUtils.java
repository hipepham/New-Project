package com.hipepham.springboot.common.excel.utility;

import org.apache.commons.lang3.StringUtils;

public class NumberUtils {

	/**
     * method helps to convert a string to Double.
     *
     * @param str the str that need convert
     * @return Double
     * @author: CuongNV34
     */
    public static Double parseDouble(final String str) {
        if (StringUtils.isNotBlank(str)) {
            try {
                return Double.parseDouble(str.trim());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
