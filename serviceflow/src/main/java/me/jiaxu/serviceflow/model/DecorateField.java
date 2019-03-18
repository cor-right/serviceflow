package me.jiaxu.serviceflow.model;

import java.util.Objects;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     对 成员变量 进行装饰
 *     在对成员变量进行赋值的时候会用到该数据结构
 */
public class DecorateField {

    /** 对应工作单元名称 */
    private String name;

    /** 成员变量 */
    private Object value;

    public DecorateField() {
    }

    public DecorateField(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecorateField that = (DecorateField) o;
        return Objects.equals(value, that.value) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, name);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
