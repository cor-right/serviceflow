package me.jiaxu.serviceflow.common.util;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Map;


/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 */
public class CollectionUtils {

    public static boolean contains(Collection collection, Object obj) {
        if (collection.isEmpty()) {
            return false;
        }
        return collection.contains(obj);
    }

    public static boolean containsNull(Collection collection) {
        return contains(collection, null);
    }

    public static boolean isEmpty(@Nullable Collection collection) {
        return org.springframework.util.CollectionUtils.isEmpty(collection);
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return org.springframework.util.CollectionUtils.isEmpty(map);
    }

}
