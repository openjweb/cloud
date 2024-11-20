package org.openjweb.sys.controller;

import org.openjweb.sys.entity.CommUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/demo/tmpl/")
public class TemplateController {

    //http://localhost:8001/demo/tmpl/queryUser

    @RequestMapping(value = "queryUser")

    public String list(Model model) {
        List<CommUser> userList = new ArrayList<>();
        CommUser user = new CommUser();
        user.setLoginId("admin");
        user.setRealName("阿宝");
        userList.add(user);
        model.addAttribute("users", userList);
        return "userlist";//不知道是否区分大小写
    }

    //http://localhost:8001/demo/tmpl/queryUser2
    @RequestMapping(value = "queryUser2")
    public ModelAndView list2() {
        List<CommUser> userList = new ArrayList<>();
        CommUser user = new CommUser();
        user.setLoginId("admin");
        user.setRealName("阿宝");
        userList.add(user);
        ModelAndView model = new ModelAndView();
        model.addObject("users",userList);
        model.setViewName("userlist");//指定视图
        return model;//这个前端没有解析出来
    }
}
