/*
 * Copyright (c) 2019.
 * Create by jiaxu.zjx on 19-2-8 下午1:55.
 */

package me.jiaxu.serviceflow.annotation;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     该注解标识微服务工作流中的对一个模型的订阅行为，订阅会在当前服务单元执行前执行。
 *     一个模型可以被多个服务单元发布，且在必须在发布才能订阅。
 */
public @interface Subscribe {
}
