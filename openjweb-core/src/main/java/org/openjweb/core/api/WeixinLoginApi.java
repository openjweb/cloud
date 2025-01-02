package org.openjweb.core.api;


import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.common.util.StringUtil;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.module.params.CommApiKeyParam;
import org.openjweb.core.service.CommApiKeyService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/api/weixin")
@RestController
@Slf4j
public class WeixinLoginApi {

    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;

    @Resource
    CommApiKeyService commApiKeyService;
    //根据域名


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void wxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //根据域名查找公司
        String domainName= CMSUtil.getDomainName(request);

        String comId = null;
        try{
          comId = service.queryForObject("select pk_id from comm_company where domain_name=?",new Object[]{domainName},String.class).toString();
        }
        catch(Exception ex){}
        if(StringUtil.isEmpty(comId)){
            comId = "C0001";
        }
        //根据公司ID获取微信开放平台的APPID
        CommApiKey keyEnt = null;
        try{
            CommApiKeyParam param = new CommApiKeyParam();
            param.setComId(comId);
            param.setApiType("OPENWEIXIN");
            keyEnt = commApiKeyService.queryList(param).get(0);
        }
        catch(Exception ex){}
        String appId = "";//微信开放平台APPID
        String appSecret = "";//微信开放平台APP密钥
        if(keyEnt!=null) {
            appId = keyEnt.getAppId()==null?"":keyEnt.getAppId();
            appSecret =  keyEnt.getAppSecret()==null?"":keyEnt.getAppSecret();
        }
        String code = request.getParameter("code");//微信登录回传的code
        String weixinUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
        String result = HttpRequest.get(weixinUrl)   .timeout(6000).execute().body();
        log.info("微信返回结果：");
        log.info(result);
        JSONObject json = JSONObject.parseObject(result);
        String accessToken = json.getString("access_token");//获取accessToken
        String openID =json.getString("openid");
        log.info("返回的accessToken和openId:::");
        log.info(accessToken);
        log.info(openID);
        //获得到accessToken和openID之后，传给统一认证

        //构造URL解析accessToken和openid
        //String url = "http://localhost:8001/oauth/authorize?response_type=code&client_id=zzyc&redirect_uri=http://localhost:8001/demo/oauth2client/callback";
        //response.sendRedirect(url);
    }
}
