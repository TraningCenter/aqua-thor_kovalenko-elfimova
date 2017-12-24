package com.netcracker.unc.utils;

import java.util.Collection;

public class CommonUtils {

    public static boolean checkEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

}
