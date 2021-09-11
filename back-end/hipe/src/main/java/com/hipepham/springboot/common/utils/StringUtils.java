package com.hipepham.springboot.common.utils;

import com.google.gson.Gson;
import com.hipepham.springboot.common.constant.IPConstants;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type String utils.
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * Whether the string is blank.
     *
     * @param str the str
     * @return boolean boolean
     */
    public static boolean isBlank(final String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Whether the the list contain blank item.
     *
     * @param list the list
     * @return the boolean
     */
    public static boolean isContainBlank(final List<String> list) {
        for (String str : list) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convert to long list.
     *
     * @param str the str
     * @return the list
     */
    public static List<Long> convertIdStrToLong(final String str) {
        String myStr = str;
        ArrayList<Long> list = new ArrayList<>();
        if (!StringUtils.isBlank(myStr)) {
            String[] arr = myStr.trim().split(IPConstants.ID_SEPARATOR);
            for (String item : arr) {
                list.add(Long.parseLong(item));
            }
        }
        return list;
    }

    /**
     * Convert to string string.
     *
     * @param ids the ids
     * @return the string
     */
    public static String convertIdsToString(final List<Long> ids) {
        return ids.stream().map(l -> l.toString())
                .collect(Collectors.joining(IPConstants.ID_SEPARATOR));
    }

    /**
     * Object is null or empty
     *
     * @param object the object
     * @return boolean boolean
     */
    public static boolean isNullorEmpty(Object object) {

        if (object instanceof String) {
            return object == null || ((String) object).isEmpty();
        }

        if (object instanceof Collection<?>) {
            return object == null || ((Collection<?>) object).isEmpty();
        }

        if (object instanceof Map<?, ?>) {
            return object == null || ((Map<?, ?>) object).isEmpty();
        }

        return object == null;
    }

    /**
     * Concat list and remove duplicate.
     *
     * @param listIP1 the list ip 1
     * @param listIP2 the list ip 2
     * @return list list
     */
    public static List<String> combineDataList(List<String> listIP1,
											   List<String> listIP2) {
        if (listIP1 == null) {
            return listIP2;
        }
        if (listIP2 == null) {
            return listIP1;
        }
        List<String> listIpNew =
                Stream.of(listIP1, listIP2).flatMap(x -> x.stream())
                        .collect(Collectors.toList());
        List<String> res =
                listIpNew.stream().distinct().collect(Collectors.toList());
        return res;
    }

    /**
     * Concat 2 list with remove item diferent.
     *
     * @param lstId the lst id
     * @param ip    the ip
     * @return list list
     */
    public static List<String> combineList(List<String> lstId,
										   List<String> ip) {
        if (lstId == null || ip == null) {
            return null;
        }
        List<String> res = lstId.stream().filter(x -> ip.contains(x))
                .collect(Collectors.toList());
        return res;
    }

    /**
     * Format data list string.
     *
     * @param foo the foo
     * @return the string
     */
    public static String formatDataList(List<String> foo) {
        String res = "";
        String json = new Gson().toJson(foo);
        res = "(" + json.substring(1, json.length() - 1) + ")";
        return res;
    }

    /**
     * Is numeric boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isNumeric(String str) {

        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        return str.chars().allMatch(Character::isDigit);

    }

    /**
     * Split big list string.
     *
     * @param list      the list
     * @param chunkSize the chunk size
     * @return list list
     */
    public static List<List<String>> splitList(List<String> list,
											   int chunkSize) {

        if (chunkSize <= 0) {
            throw new IllegalArgumentException("Invalid chunk size: " + chunkSize);
        }
        if (list == null) {
            return null;
        }
        if (list.size() < chunkSize) {
            List<List<String>> listSmall = new ArrayList<>();
            listSmall.add(list);
            return listSmall;
        }
        List<List<String>> chunkList = new ArrayList<>(list.size() / chunkSize);
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunkList.add(list.subList(i,
                    i + chunkSize >= list.size() ? list.size()
                            : i + chunkSize));
        }
        return chunkList;
    }

    /**
     * Convert list to json string.
     *
     * @param foo the foo
     * @return the string
     */
    public static String convertListToJson(List<String> foo) {
        String res = "";
        for (int i = 0; i < foo.size(); i++) {
            res = res + "\"" + foo.get(i) + "\"";
            if (i < foo.size() - 1) {
                res = res + ",";
            }
        }
        return res;
    }

    /**
     * String normalize string.
     *
     * @param str     the str
     * @param regex   the regex
     * @param spliter the spliter
     * @return the string
     */
    public static String stringNormalize(
            final String str, final String regex, final String spliter) {
        String nfdNormalizedString =
                Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern =
                Pattern.compile("\\p{Block=CombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("")
                .replaceAll("Đ", "D").replaceAll("đ", "d")
                .replaceAll(regex, spliter);
    }


    /**
     * To camel case string.
     *
     * @param s the s
     * @return the string
     */
    public static String toCamelCase(final String s){
        String[] parts = s.split("_");
        StringBuilder camelCaseString = new StringBuilder();
        for (String part : parts){
            camelCaseString.append(toProperCase(part));
        }
        return camelCaseString.toString();
    }

    /**
     * To proper case string.
     *
     * @param s the s
     * @return the string
     */
    public static String toProperCase(final String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }

    /**
     * List to sqlcondition in string.
     *
     * @param list the list
     * @return the string
     */
    public static String listToSqlconditionIn(final List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : list) {
            stringBuilder.append("'").append(item).append("'").append(",");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
