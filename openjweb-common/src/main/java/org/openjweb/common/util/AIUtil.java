package org.openjweb.common.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class AIUtil {

    public static String getDeepSeekChatResult(String baseUrl, String key,String content) {


        String url  = "https://api.deepseek.com/chat/completions";

        //HttpClientUtils client = new HttpClientUtil();
        JSONObject json = new JSONObject();
        json.put("model","deepseek-chat");
        json.put("stream",false);
        JSONArray messArray = new JSONArray();
        JSONObject tmpJson = new JSONObject();
        tmpJson.put("role","system");
        tmpJson.put("content",content);
        messArray.add(tmpJson);
        json.put("messages",messArray);
        System.out.println(json.toJSONString());
        //result = HttpRequest.post(tmpUrl)   .timeout(6000).execute().body();
        String result = HttpRequest.post(url )
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+key)
                .body(json.toJSONString())
                //.header("User-Agent", "MyApp/1.0")
                //.header("Accept", "application/json")
                .timeout(20000).execute().body();
        //得充值
        //{"error":{"message":"Insufficient Balance","type":"unknown_error","param":null,"code":"invalid_request_error"}}


        return result;
   }

   public static void main(String[] args){
       String s = AIUtil.getDeepSeekChatResult("","","请给出订单表的字段中文名");
       System.out.println(s );
    }
}
