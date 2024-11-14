package org.openjweb.sys.api;


import lombok.extern.slf4j.Slf4j;
import org.openjweb.redis.starter.util.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//测试：http://localhost:8001/demo/api/redis/set?key=name&value=abao
//测试：http://localhost:8001/api/redis/get?key=name
//测试：http://localhost:8001/api/redis/get2?key=name

@Slf4j
@RequestMapping("/demo/api/redis")
@RestController
public class RedisDemoApi {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("set")
    public boolean redisset(String key, String value){
        key = "name";// 生产环境中这个接口不能暴露，并且程序中不使用name，或者注释掉@RequestMapping("/demo/api/redis")
        log.info("传入的key和value:"+key+"--"+value);
        //默认秒数
        //return redisUtil.set(key,value);
        //指定秒数
        boolean bool = redisUtil.set(key,value,60);//设置1分钟有效时长
        return bool;

    }

    @RequestMapping("get")
    public Object redisget(String key){
        key = "name";// 生产环境中这个接口不能暴露
        log.info("传入的key:");
        log.info(key);
        log.info(String.valueOf(redisUtil.get(key)));
        return redisUtil.get(key);
    }

    @RequestMapping("get2")
    public Object redisget2(String key){
        key = "name";// 生产环境中这个接口不能暴露
        String value = stringRedisTemplate.opsForValue().get(key);
        log.info("使用stringRedisTemplate获取：");
        log.info(value);//这个返回带引号的
        value = String.valueOf(redisTemplate.opsForValue().get(key));
        log.info("使用redisTemplate获取：");
        log.info(value);//这个返回带引号的



        return value;
    }

    @RequestMapping("expire")
    public boolean expire(String key,long ExpireTime){
        key = "name";// 生产环境中这个接口不能暴露
        return redisUtil.expire(key,ExpireTime);
    }

}
