package me.jiaxu.serviceflow.common.util;

import lombok.Getter;
import lombok.Setter;
import me.jiaxu.serviceflow.SpringServiceFlowStarterWithOrderManager;
import me.jiaxu.serviceflow.annotation.In;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by jiaxu.zjx on 2019/3/19
 * Description:
 */
public class ReflectUtilsTest {

    @In
    @Setter
    @Getter
    private String demoString;

    /**
     * contains annotation test
     *
     * @throws NoSuchFieldException
     */
    @Test
    public void containsAnnotationTest() throws NoSuchFieldException {
        for (Field field : ReflectUtilsTest.class.getDeclaredFields()) {
            System.out.println(field.getName());
        }
        Boolean contains = ReflectUtils.containsAnnotation(
                ReflectUtilsTest.class.getDeclaredField("demoString"), In.class);
        System.out.println("contains: " + contains);
    }

    @Test
    public void getNameTest() {
        System.out.println(SpringServiceFlowStarterWithOrderManager.class.getName());
        System.out.println(SpringServiceFlowStarterWithOrderManager.class.getCanonicalName());
        System.out.println(SpringServiceFlowStarterWithOrderManager.class.getSimpleName());
        System.out.println(SpringServiceFlowStarterWithOrderManager.class.getTypeName());
    }

}
