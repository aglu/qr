package com.qr.dbcontroller.util;

import java.util.Collection;
import java.util.Map;

abstract public class MiscUtil {

    public static boolean isEmpty(CharSequence source) {
        return source == null || source.length() == 0;
    }


    public static boolean isEmpty(Number source) {
        return source == null || source.doubleValue() == 0;
    }


    public static boolean isEmpty(Collection<?> source) {
        return source == null || source.size() == 0;
    }


    public static boolean isEmpty(Map<?, ?> source) {
        return source == null || source.size() == 0;
    }


    public static boolean isEmpty(Object[] source) {
        return source == null || source.length == 0;
    }

}
