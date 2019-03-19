package me.jiaxu.demo.biz.payment;

import me.jiaxu.demo.model.BaseResponse;
import me.jiaxu.demo.model.PaymentModel;
import me.jiaxu.serviceflow.ServiceUnit;
import me.jiaxu.serviceflow.annotation.Out;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 */
public class RenderResponse implements ServiceUnit {

    @Out
    private BaseResponse<PaymentModel> response;

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
