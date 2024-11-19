package org.openjweb.sys.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.response.ResponseResult;
import org.openjweb.sys.entity.CommUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * http://localhost:8001/demo/queryParam?parmName=version
 * */
@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoDBApi {
    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;

    @Resource(name = "jdbcTemplateTwo")
    private JdbcTemplate service2;

    @GetMapping("queryParam")
    public JSONObject queryParam(@RequestParam(value = "parmName")String parmName){
        //查询count
        Integer count = service.queryForObject("select count(*) from comm_system_parms",Integer.class);
        JSONObject json = new JSONObject();
        json.put("num",count);
        //下面按查询条件查询--查单个值
        parmName = "version";//因为是演示程序，这里不从请求中获取参数
        String sql = "select parm_value from comm_system_parms where parm_name=?";
        String parmValue = service.queryForObject(sql,new Object[]{parmName},String.class);
        log.info(parmValue);//使用log.info需要在类前面加@Slf4j 注解
        //查询列表
        JSONArray jsonArray = new JSONArray();
        String parmLike ="version%";
        String sql1 = "select parm_name,parm_value from comm_system_parms where parm_name like ?";
        List<Map<String,Object>> list = service.queryForList(sql1,new Object[]{parmLike});
        if(list!=null&&list.size()>0){
            for (Map<String,Object> data:list   ) {
                JSONObject dataJson = new JSONObject();
                dataJson.put("parmName",data.get("parm_name")==null?"":data.get("parm_name").toString());
                dataJson.put("parmValue",data.get("parm_value")==null?"":data.get("parm_value").toString());
                jsonArray.add(dataJson);
            }
        }
        //如果结果集顶多一条记录，查询一条记录可使用queryForMap

        Map<String,Object> map = null;
        //这次使用第二个数据源（为了演示方便，链接配置实际指向一个数据库）
        map = service2.queryForMap("select * from comm_system_parms where parm_name=?",new Object[]{parmName});
        if(map!=null){
            log.info("map查到数据：");
            log.info(map.get("parm_value")==null?"":map.get("parm_value").toString());


        }
        json.put("count",count);
        json.put("data",jsonArray);
        return json;
    }

    /**
     * http://localhost:8001/demo/queryUser2
     * @return
     */

    @GetMapping("queryUser2")

    public ResponseResult getStudent(){
        CommUser user = new CommUser();
        user.setLoginId("abao");
        user.setRealName("王先生");
        user.setUserSex("男");

        return ResponseResult.okResult(user);
    }
}
