package me.jiaxu.serviceflow;

import java.util.List;

/**
 * Created by jiaxu.zjx on 2019/3/18
 * Description:
 *     工作流流程定义接口，每一个该接口的实现（流程定义对象）都定义了一条工作流
 */
public interface FlowOrderManager {

    /**
     * 进行工作流流程的定义，列表中的字符串为 工作单元实现类 的 全限定名
     *
     * @return
     */
    List<String> serviceUnitsOrderList();

}
