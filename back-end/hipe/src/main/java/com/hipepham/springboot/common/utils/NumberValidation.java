package com.hipepham.springboot.common.utils;

public class NumberValidation {
    public static boolean isNumeric(String strNum) {
        if (strNum == null || "".equalsIgnoreCase(strNum)) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null || "".equalsIgnoreCase(strNum)) {
            return false;
        }
        try {
            Integer d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isIntegerRange(String strNum, String fromInt, String toInt ) {
        if (strNum == null || "".equalsIgnoreCase(strNum)) {
            return false;
        }
        try {
            Integer d = Integer.parseInt(strNum);
            Integer from = Integer.parseInt(fromInt);
            Integer to = Integer.parseInt(toInt);
            if(d>=from && d<=to){
                return true;
            }else{
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        //return true;
    }

    public static boolean isFloatRange(String strNum, String fromInt, String toInt ) {
        if (strNum == null || "".equalsIgnoreCase(strNum)) {
            return false;
        }
        try {
            Float d = Float.parseFloat(strNum);
            Float from = Float.parseFloat(fromInt);
            Float to = Float.parseFloat(toInt);
            if(d>=from && d<=to){
                return true;
            }else{
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        //return true;
    }
}
