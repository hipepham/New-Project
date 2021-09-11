package com.hipepham.springboot.common.utils;

public class CleanXssUtil {
    /* fix security bug 2 start */
    public static String cleanXSS(String value) {
//        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//        value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
//        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
//        value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
//        value = value.replaceAll("'", "&#39;");
//        value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("[\\\"\\\'][\\s]*onc(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("[\\\"\\\'][\\s]*onerr(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("[\\\"\\\'][\\s]*alert(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("script", "sscript");

//        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
//        String safeHTML = policy.sanitize(value);

        return value;
    }
    /* fix security bug 2 end */
}
