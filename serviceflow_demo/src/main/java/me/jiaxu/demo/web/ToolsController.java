package me.jiaxu.demo.web;

import me.jiaxu.serviceflow.ServiceFlowTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jiaxu.zjx on 2019/3/19
 * Description:
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@Controller
public class ToolsController {

    @Autowired
    private ServiceFlowTools serviceFlowTools;

    @RequestMapping(value = "/relation", method = RequestMethod.GET)
    public ModelAndView relations() {
        ModelAndView mav=new ModelAndView();

        mav.addObject("table", serviceFlowTools.generateRelationMapInHTML());
        mav.setViewName("relationShow");
        return mav;
    }


}
