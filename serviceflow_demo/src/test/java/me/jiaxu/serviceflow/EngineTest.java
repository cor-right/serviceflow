package me.jiaxu.serviceflow;

import me.jiaxu.demo.Application;
import me.jiaxu.demo.biz.Payment;
import me.jiaxu.demo.biz.Refund;
import me.jiaxu.demo.model.BaseRequest;
import me.jiaxu.demo.model.BaseResponse;
import me.jiaxu.demo.model.PaymentModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jiaxu.zjx on 2019/3/20
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class EngineTest {

    @Autowired
    private Payment payment;

    @Autowired
    private Refund refund;

    @Test
    public void applyTest() {
        BaseResponse<PaymentModel> apply = payment.apply(new BaseRequest());
        System.out.println("service done");
    }

}
