package me.jiaxu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     启动类
 */
@SpringBootApplication
@ComponentScans(value = {
        @ComponentScan(value = "me.jiaxu.serviceflow"),
        @ComponentScan(value = "me.jiaxu.demo")
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
