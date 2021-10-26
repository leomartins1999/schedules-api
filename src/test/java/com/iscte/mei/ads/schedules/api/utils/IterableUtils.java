package com.iscte.mei.ads.schedules.api.utils;

import java.util.ArrayList;
import java.util.List;

public class IterableUtils {

    public static <T> List<T> iterableToList(Iterable<T> it) {
        List<T> list = new ArrayList<T>();

        for (T t : it) list.add(t);

        return list;
    }

}
