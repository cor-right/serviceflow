package me.jiaxu.demo.biz;

import me.jiaxu.serviceflow.FlowOrderManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     退款
 */
@Service
public class Refund  implements FlowOrderManager {

    @Override
    public List<String> serviceUnitsOrderList() {
        return Arrays.asList(
                "me.jiaxu.demo.biz.common.UserValidate",
                "me.jiaxu.demo.biz.common.SellerValidate",
                "me.jiaxu.demo.biz.common.ItemValidate",
                "me.jiaxu.demo.biz.refund.ModifyOrder",
                "me.jiaxu.demo.biz.refund.RefundDiscount",
                "me.jiaxu.demo.biz.refund.RefundMoney",
                "me.jiaxu.demo.biz.refund.RenderResponse"
        );
    }

}
