package me.jiaxu.demo.biz.payment;

import me.jiaxu.demo.model.BaseRequest;
import me.jiaxu.serviceflow.ServiceUnit;
import me.jiaxu.serviceflow.annotation.In;
import me.jiaxu.serviceflow.annotation.Publish;
import me.jiaxu.serviceflow.annotation.Subscribe;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 */
public class CreateOrder implements ServiceUnit {

    @In
    private BaseRequest request;

    @Subscribe
    private Object discountModel;

    @Subscribe
    private Object paymentModel;

    @Publish
    private Object orderModel;

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Override
    public void process() {

    }
}
