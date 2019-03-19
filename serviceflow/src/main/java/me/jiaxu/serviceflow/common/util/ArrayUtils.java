package me.jiaxu.serviceflow.common.util;

import java.util.Arrays;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     数组工具类
 */
public class ArrayUtils {

    public static Boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判定包含关系
     *
     * @param array array
     * @param elem elem
     * @return true / false
     */
    public static Boolean contains(Object[] array, Object elem) {
        if (array == null || array.length == 0) {
            return false;
        }
        return Arrays.asList(array).contains(elem);
    }



}
