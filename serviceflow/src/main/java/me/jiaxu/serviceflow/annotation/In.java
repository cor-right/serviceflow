/*
 * Copyright (c) 2019.
 * Create by jiaxu.zjx on 19-2-8 下午1:23.
 */

package me.jiaxu.serviceflow.annotation;

import java.lang.annotation.*;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     该注解标识微服务工作流中的外部入参，外部入参在每一个工作流中都是唯一的。
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface In {
}
