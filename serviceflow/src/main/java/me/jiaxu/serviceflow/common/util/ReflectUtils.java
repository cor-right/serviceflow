package me.jiaxu.serviceflow.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiaxu.zjx on 2019/3/19
 * Description:
 *     反射工具类
 */
public class ReflectUtils {

    /**
     * 判定某个 field 上是否有某个注解对象存在
     *
     * @param field field
     * @param annotation 注解类型，例如 Autowried.class
     * @return true / false: 含有 / 不含有
     */
    public static Boolean containsAnnotation(Field field, Class annotation) {
        // 获得 field 上的所有注解的 type
        List fieldAnnoTypes = Arrays
                .stream(field.getDeclaredAnnotations())
                .map(Annotation::annotationType)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fieldAnnoTypes) || annotation == null) {
            return false;
        }

        for (Object fieldAnnoType : fieldAnnoTypes) {
            if (fieldAnnoType.equals(annotation)) {
                return true;
            }
        }
        return false;
    }

}
