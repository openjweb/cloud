package org.openjweb.cms.api;


//测试：http://localhost:8001/demo/api/redis/set?key=name&value=abao
//测试：http://localhost:8001/api/redis/get?key=name


import lombok.extern.slf4j.Slf4j;
import org.openjweb.redis.starter.util.RedisUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RequestMapping("/demo/api/cms/redis")
@RestController
public class CmsRedisDemoApi {
    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("set")
    public boolean redisset(String key, String value){
        log.info("传入的key和value:"+key+"--"+value);
        //默认秒数
        //return redisUtil.set(key,value);
        //指定秒数
        boolean bool = redisUtil.set(key,value,60);//设置1分钟有效时长
        return bool;

    }

    @RequestMapping("get")
    public Object redisget(String key){
        log.info("传入的key:");
        log.info(key);
        log.info(String.valueOf(redisUtil.get(key)));
        return redisUtil.get(key);
    }

    @RequestMapping("expire")
    public boolean expire(String key,long ExpireTime){
        return redisUtil.expire(key,ExpireTime);
    }

}