package org.openjweb.cms.controller;

import org.openjweb.cms.lucene.ChineseSearch;
import org.openjweb.cms.service.LuceneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/demo/lucene")
public class IndexController {
    //http://localhost:8001/demo/lucene/test

    @Resource
    private LuceneService luceneService;


    @GetMapping("/test2")
    public String test2(Model model) {
        //http://localhost:8001/demo/lucene/test2
        // 索引所在的目录
        String indexDir = "D:\\lucene\\cms";
        String searchContent = "兼职";
       try {
            List<String> list = luceneService.search(indexDir, searchContent);
            model.addAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("/test")
    public String test(Model model) {
        // 索引所在的目录
        String indexDir = "D:\\lucene2";
        // 要查询的字符
//        String q = "南京文明";
        //String q = "南京文化";
        String q = "security";

        try {
            List<String> list = ChineseSearch.search(indexDir, q);
            model.addAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "result";
    }

}
