package me.jiaxu.demo.biz.payment;

import me.jiaxu.demo.model.BaseResponse;
import me.jiaxu.demo.model.PaymentModel;
import me.jiaxu.serviceflow.ServiceUnit;
import me.jiaxu.serviceflow.annotation.Out;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 */
@Service(value = "me.jiaxu.demo.biz.payment.RenderResponse")
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
