package me.jiaxu.serviceflow.annotation.engine;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.lang.annotation.*;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     可继承的原型模式注解
 *     原型模式在spring中指，每次 *注入* 或 *从上下文中获* 的时候，都会创建新的实例
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface PrototypeScope {
}
