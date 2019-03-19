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
public class SpringContextTest {

    @Autowired
    private SpringContext springContext;

    @Test
    public void registryBeanTest() {
//        new Payment().serviceUnitsOrderList().forEach(name -> {
//
//            Map<String, DecorateField> publishMap = new HashMap<>();
//            Object request = new BaseRequest();
//
//            try {
//                springContext.registryBean(name, publishMap, request);
//            } catch (ServiceFlowEngineStartException e) {
//                e.printStackTrace();
//            }
//
//        });
    }


}
