package org.openjweb.core.api;


import org.openjweb.core.service.CommApiKeyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/api/weixin")
@RestController
public class WeixinLoginApi {

    @Resource
    CommApiKeyService commApiKeyService;



    //从配置中获取微信开放平台的appId和Key?,但对于SaaS模式不适合



    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void wxLogin(HttpServletResponse response) throws IOException {


        String url = "http://localhost:8001/oauth/authorize?response_type=code&client_id=zzyc&redirect_uri=http://localhost:8001/demo/oauth2client/callback";
        response.sendRedirect(url);


    }

}
