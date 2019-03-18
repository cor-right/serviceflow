package me.jiaxu.demo.biz;

import me.jiaxu.demo.model.BaseRequest;
import me.jiaxu.demo.model.BaseResponse;
import me.jiaxu.demo.model.PaymentModel;
import me.jiaxu.serviceflow.FlowOrderManager;
import me.jiaxu.serviceflow.SpringServiceFlowStarterWithOrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     支付链路
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Payment implements FlowOrderManager {

    @Autowired
    private SpringServiceFlowStarterWithOrderManager<BaseRequest, BaseResponse<PaymentModel>> starter;

    /**
     * 支付工作流流程定义
     *
     * @return
     */
    @Override
    public List<String> serviceUnitsOrderList() {
        return Arrays.asList(
                "me.jiaxu.demo.biz.common.UserValidate",
                "me.jiaxu.demo.biz.common.SellerValidate",
                "me.jiaxu.demo.biz.common.ItemValidate",
                "me.jiaxu.demo.biz.payment.DiscountCalculate",
                "me.jiaxu.demo.biz.payment.PaymentCalculate",
                "me.jiaxu.demo.biz.payment.MoneyExamination",
                "me.jiaxu.demo.biz.payment.CreateOrder",
                "me.jiaxu.demo.biz.payment.RenderResponse"
        );
    }

    public BaseResponse<PaymentModel> apply(BaseRequest request) {
        try {
            return starter.apply(request, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
