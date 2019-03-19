package me.jiaxu.demo.biz.payment;

import me.jiaxu.demo.model.BaseRequest;
import me.jiaxu.serviceflow.ServiceUnit;
import me.jiaxu.serviceflow.annotation.In;
import me.jiaxu.serviceflow.annotation.Publish;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 */
public class DiscountCalculate implements ServiceUnit {

    @In private BaseRequest baseRequest;

    @Publish private Object discountModel;

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
