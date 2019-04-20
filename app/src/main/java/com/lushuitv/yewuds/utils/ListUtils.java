package com.lushuitv.yewuds.utils;

import java.util.List;

/**
 * @author weip
 */

public class ListUtils {

    public static boolean isEmpty(List list){
        if (list == null){
            return true;
        }
        return list.size() == 0;
    }
}
