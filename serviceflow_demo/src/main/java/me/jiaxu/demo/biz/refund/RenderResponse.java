package me.jiaxu.demo.biz.refund;

import me.jiaxu.demo.model.BaseResponse;
import me.jiaxu.serviceflow.ServiceUnit;
import me.jiaxu.serviceflow.annotation.Out;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 */
public class RenderResponse implements ServiceUnit {

    @Out
    private BaseResponse response;

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
