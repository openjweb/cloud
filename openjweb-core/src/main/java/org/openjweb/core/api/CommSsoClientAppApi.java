package org.openjweb.core.api;

import org.openjweb.core.entity.CommSsoClientApp;
import org.openjweb.core.service.CommSsoClientAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试地址：http://localhost:8001/demo/ssoclient/queryAccount?account=zzyc
 */
@RequestMapping("/demo/ssoclient")
@RestController
public class CommSsoClientAppApi {

    @Autowired
    private CommSsoClientAppService commSsoClientAppService;

    @RequestMapping("queryAccount")
    public String queryAccount(String account){
        CommSsoClientApp ent = this.commSsoClientAppService.selectByAccountId(account);
        if(ent!=null){
            return ent.getClientName();
        }
        return "没查到!";
    }
}
