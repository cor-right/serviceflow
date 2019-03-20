package me.jiaxu.demo.biz.refund;

import me.jiaxu.demo.model.BaseRequest;
import me.jiaxu.serviceflow.ServiceUnit;
import me.jiaxu.serviceflow.annotation.In;
import me.jiaxu.serviceflow.annotation.Publish;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 */
public class ModifyOrder implements ServiceUnit {

    @In private BaseRequest request;

    @Publish private Object orderModel;

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Override
    public void process() {
        System.out.println(this.getClass().getName());
    }
}
