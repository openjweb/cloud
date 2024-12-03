package org.openjweb.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http://localhost:8001/demo/ftl/queryBook
 */
@Controller
@RequestMapping(value="/demo/ftl/")
public class BooksDemoController {

    @RequestMapping(value="/queryBook")
    //@GetMapping("/queryUser")
    public String queryBook( Model model) {

        List<Map<String,Object>> bookList = new ArrayList();
        Map<String,Object> map = new HashMap();
        map.put("name", "三国演义");
        map.put("author", "罗贯中");
        bookList.add(map);
        map = new HashMap();
        map.put("name", "红楼梦");
        map.put("author", "曹雪琴");
        bookList.add(map);
        model.addAttribute("books", bookList);
        return "books";//返回页面名
    }
}
