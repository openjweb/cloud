package org.openjweb.core.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BeetlService implements Function {

    public List<Map<String,Object>> getCateInfoListDemo(String treeCode ){

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("infTitle","国庆节来了"+treeCode);
        map.put("infUrl","http://www.sohu.com");
        map.put("Image","");
        list.add(map);
        map = new HashMap<>();
        map.put("infTitle","2025年春节放假通知"+treeCode);
        map.put("infUrl","http://www.sohu.com");
        map.put("Image","");
        list.add(map);
        return list;
    }

    @Override
    public Object call(Object[] objects, Context context) {

        if(objects.length>1){
            log.info("第二个参数pageNo："+String.valueOf(objects[1]));

        }
        if(objects.length>2){
            log.info("第三个参数pageSize："+String.valueOf(objects[2]));

        }
        if(1==2) {

            return this.getCateInfoListDemo((String )objects[0]);
        }
        else {
            log.info("传入的栏目编码为:::::");
            log.info(String.valueOf(objects[0]));

            return this.getCateInfoList((String) objects[0], 1, 10);
        }

    }
    public Object getCateInfoList(String treeCode ,int pageNo,int pageSize){
        HttpRequest request = HttpRequest.get("http://localhost:8001/api/cms/pub/getCateInfoList?treeCode="+treeCode+"&pageNo="+pageNo+"&pageSize="+pageSize);
        HttpResponse response = request.execute();
        String result = response.body();
        log.info("请求返回的内容：");
        log.info(result);
        return JSONArray.parseArray(result) ;
    }


}
