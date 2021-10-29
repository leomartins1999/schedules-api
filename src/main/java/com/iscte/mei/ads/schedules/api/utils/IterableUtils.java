package com.iscte.mei.ads.schedules.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class IterableUtils {

    public static <T> List<T> iterableToList(Iterable<T> it) {
        List<T> list = new ArrayList<T>();

        for (T t : it) list.add(t);

        return list;
    }

    public static <T, V> Iterable<V> map(Iterable<T> values, Function<T, V> mapper) {
        ArrayList<V> result = new ArrayList<>();

        for (T value : values) result.add(mapper.apply(value));

        return result;
    }

}
