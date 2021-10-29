package com.iscte.mei.ads.schedules.api.utils;

public class StringUtils {

    public static String nullIfEmpty(String s) {
        return s.isEmpty() || s.isBlank() ? null : s;
    }

}
