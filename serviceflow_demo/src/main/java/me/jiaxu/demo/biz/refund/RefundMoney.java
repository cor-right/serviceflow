package me.jiaxu.demo.biz.refund;

import me.jiaxu.serviceflow.ServiceUnit;
import me.jiaxu.serviceflow.annotation.Publish;
import me.jiaxu.serviceflow.annotation.Subscribe;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 */
public class RefundMoney implements ServiceUnit {

    @Subscribe private Object orderModel;

    @Subscribe private Object discountModel;

    @Publish private Object moneyModel;

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
