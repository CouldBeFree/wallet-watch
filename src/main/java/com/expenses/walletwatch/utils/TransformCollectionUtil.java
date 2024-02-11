package com.expenses.walletwatch.utils;

import java.util.Collection;
import java.util.List;

public class TransformCollectionUtil {
    public static Object[] flat(List<List> value) {
        return value.stream().flatMap(Collection::stream).toArray();
    }
}
