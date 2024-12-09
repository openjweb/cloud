package org.openjweb.sys.controller;


import org.openjweb.core.entity.CommUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http://localhost:8001/demo/beetl/queryBook
 */
@Controller
@RequestMapping(value="/demo/beetl/")
public class BeetlDemoController {

    @RequestMapping(value="/queryBook")

    public String queryBook( Model model) {


        Map<String,Object> map = new HashMap();
        map.put("name", "三国演义");
        map.put("author", "罗贯中");

        model.addAttribute("book", map);
        //如果在application-dev.yml不指定后缀的话，默认是.btl后缀。
        return "beetlDemo.btl";//返回页面名
    }

    //http://localhost:8001/demo/beetl/queryBook2
    @RequestMapping(value="/queryBook2")

    public String queryBook2( Model model) {


        Map<String,Object> map = new HashMap();
        map.put("name", "三国演义");
        map.put("author", "罗贯中");

        model.addAttribute("book", map);
        return "beetlDemo2.html";//返回页面名
    }

    //http://localhost:8001/demo/beetl/demo

    /**
     * 演示beetl语法
     * @param model
     * @return
     */
    @RequestMapping(value="/demo")

    public String demo( Model model) {

        CommUser user = new CommUser();
        user.setRealName("张三");
        user.setUserEmail("abc@163.com");
        List<CommUser> list = new ArrayList<>();
        list.add(user );

        user = new CommUser();

        user.setRealName("李四");
        user.setUserEmail("lisi@163.com");
        list.add(user );


        model.addAttribute("users", list);
        return "beetlDemo3.html";//返回页面名
    }

    //http://localhost:8001/demo/beetl/demofile


    @RequestMapping(value="/demofile")

    public String demofile( Model model) {

        CommUser user = new CommUser();
        user.setRealName("张三");
        user.setUserEmail("abc@163.com");
        List<CommUser> list = new ArrayList<>();
        list.add(user );

        user = new CommUser();

        user.setRealName("李四");
        user.setUserEmail("lisi@163.com");
        list.add(user );


        model.addAttribute("users", list);
        return "b2c/beetlDemo3.html";//返回页面名
    }



}
