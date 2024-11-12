package org.openjweb.sys.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.openjweb.sys.entity.CommUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ucenter")
@Api(tags = "admin-用户管理")
public class CommUserApi {

    @ApiOperation("用户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", paramType = "query"),
            @ApiImplicitParam(name = "province", value = "所属省份", paramType = "query")
    })

    @GetMapping("info")

    public CommUser getUserInfo(@RequestParam(value = "username")String username, @RequestParam(value = "province")String province){
    //public CommUser getUserInfo( String username, String province){

        CommUser user = new CommUser();
        user.setUserSex("男");
        user.setUsername(username);
        user.setRealName("王先生");
        user.setLoginId("abao");
        return user;
    }
}
