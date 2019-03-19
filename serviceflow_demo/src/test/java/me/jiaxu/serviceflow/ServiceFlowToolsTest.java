package me.jiaxu.serviceflow;

import me.jiaxu.demo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jiaxu.zjx on 2019/3/19
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ServiceFlowToolsTest {

    @Autowired
    private ServiceFlowTools serviceFlowTools;

    @Test
    public void loadTest() {
        serviceFlowTools.getFlowManagerList().forEach(flow -> System.out.println(flow.getClass().getSimpleName()));
        System.out.println();
        serviceFlowTools.getServiceUnitMap().forEach((l, r) -> {
            System.out.println(l);
            r.forEach(unit -> System.out.println(unit.getClass().getSimpleName()));
        });
        System.out.println();

        System.out.println(serviceFlowTools.getInMap());
        System.out.println(serviceFlowTools.getOutMap());
        System.out.println(serviceFlowTools.getPublishMap());
        System.out.println(serviceFlowTools.getSubscribeMap());
    }

    @Test
    public void generateRelationMapInHTMLTest() {
        System.out.println(serviceFlowTools.generateRelationMapInHTML());
    }

}
