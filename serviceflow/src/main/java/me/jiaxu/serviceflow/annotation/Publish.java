/*
 * Copyright (c) 2019.
 * Create by jiaxu.zjx on 19-2-8 下午1:55.
 */

package me.jiaxu.serviceflow.annotation;

/**
 * Created by jiaxu.zjx on 2019/2/8
 * Description:
 *     该注解标识微服务工作流中的对一个模型的发布行为，发布会在当前服务单元执行完成后执行。
 *     一个模型只能被一个服务单元发布，且在发布前不能被订阅。
 */
public @interface Publish {
}
