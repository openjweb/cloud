package org.openjweb.cms.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.cms.service.CmsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "内容管理-前端查询")
@Slf4j
@Controller
@RequestMapping("/front") //

public class CmsInfoController {
    @Autowired
    private CmsInfoService cmsInfoService;

    @RequestMapping(value="/index")

    public String index( Model model) {


        /*Map<String,Object> map = new HashMap();
        map.put("name", "三国演义");
        map.put("author", "罗贯中");

        model.addAttribute("book", map);*/
        return "cms/site/wenhua/index.html";//返回页面名
    }

}
