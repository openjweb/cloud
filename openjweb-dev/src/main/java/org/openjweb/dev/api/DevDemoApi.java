package org.openjweb.dev.api;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.openjweb.common.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/***
 * @author abao
 */
//localhost:8001/demo/dev/code
@Slf4j
@RestController
@RequestMapping("/demo/dev")
public class DevDemoApi {

    @Value("${beetl.fileTemplatesPath:}") String fileTemplatesPath;
    @RequestMapping("code")
    private String createBeetlCode(){
        log.info("调用/demo/dev/createCode接口................");
        FileResourceLoader resourceLoader = new FileResourceLoader(fileTemplatesPath,"utf-8");
        Configuration cfg = null;
        try {

            cfg = Configuration.defaultConfiguration();
            cfg.setCharset("utf-8");//设置文件的字符集

        } catch (IOException e) {
            e.printStackTrace();
        }
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("dev/ApiTemplate.java");


        t.binding("authorName","阿宝11");
        t.binding("packageName","org.openjweb.dev.demo");
        t.binding("classRequestMapping","/demo/dev/hello");
        t.binding("className","MyDemo");
        t.binding("varName","filePath");
        String str = null;
        try{
            str = t.render();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return "运行错误:"+ex.toString();
        }
        log.info("生成的文件内容:"+str);
        //生成到文件
        try {
            FileUtil.str2file(str,"d:/tmp/beetl/templates/dev/MyDemo.java","utf-8");
        } catch (Exception e) {

            e.printStackTrace();
            return "文件生成异常："+e.toString();

        }
        //System.out.println(str);
        return str;
    }
}