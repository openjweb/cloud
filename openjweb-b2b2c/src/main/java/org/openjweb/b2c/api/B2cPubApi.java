package org.openjweb.b2c.api;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.b2c.entity.B2cArea;
import org.openjweb.b2c.module.params.B2cAreaParam;
import org.openjweb.common.response.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/b2c/pub/")
@Slf4j
public class B2cPubApi {
    //http://localhost:8001/api/b2c/pub/demo
    @RequestMapping("/demo")
    public  ResponseResult edit(){
        return  ResponseResult.okResult(0,"success");

    }
    public ResponseResult queryMemberInfo(){
        return null;

    }
    //应用的签名怎么管理？单位、应用、appId、密钥、应用编码

}
