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
public class PaymentCalculate implements ServiceUnit {

    @In private BaseRequest request;

    @Subscribe private Object discountModel;

    @Publish private Object paymentModel;

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
