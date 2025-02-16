package org.openjweb.sys.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.sys.entity.CommUser;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/ucenter")
@Api(tags = "admin-用户管理")
@Slf4j
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

    @RequestMapping(value = "/getVueLoginStyle", method = {RequestMethod.POST, RequestMethod.GET}) // ,RequestMethod.GET
    public @ResponseBody
    JSONObject getVueLoginStyle(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        {
            log.info("调用getVueLoginStyle...........");


            HashMap<String, Object> map = new HashMap();
            String appId = request.getParameter("appId") == null ? "" : request.getParameter("appId");// 登录账号，可根据登录账号设置登录页样式
            JSONObject dataJson = new JSONObject();
            JSONObject resultJson = new JSONObject();

            String isExcelMenu = "false";// 是否excel传来的
            String cookieValue = "";
            String appName = "";
            String appIcon = "";



            String sysName = "测试";
            String logoPath = "";



            if (appId.equals("1")) {
                resultJson.put("code", 0);
                resultJson.put("msg", "接口调用成功!");
                dataJson.put("contract", "这里是用户协议........");// 用户协议文本
                dataJson.put("appId", appId);// 参数回传
                dataJson.put("picPath", "https://c0001-1.zzyicheng.cn/vue/static/img/background.c7e07de7.jpg");// 背景图片
                dataJson.put("contactMsg", "客服微信:openjweb");// 联系信息
                dataJson.put("comName", "北京众智益成科技有限公司");
                dataJson.put("sysName", (sysName == null || sysName.trim().length() == 0) ? "后台管理" : sysName);
                dataJson.put("showSinaButton", "N");// 是否显示新浪按钮，是为Y N 为否 默认显示
                dataJson.put("showWeixinButton", "Y");// 是否显示微信按钮，是为Y N 为否 默认显示
                dataJson.put("showQQButton", "N");// 是否显示QQ按钮，是为Y N 为否 默认显示
                dataJson.put("sinaQRImage", "");// 新浪二维码扫码图片
                dataJson.put("qqQRImage", "");// QQ二维码扫码图片
                dataJson.put("weixinQRImage", "");// 微信二维码扫码图片
                dataJson.put("sinaLoginUrl", "");// 新浪登录链接（备用）
                dataJson.put("qqLoginUrl", "");// QQ登录链接（备用）
                dataJson.put("weixinLoginUrl", "");// 微信登录链接（备用）
                dataJson.put("logoPath", logoPath);// 登录页图标链接
                dataJson.put("serviceWeixin", "12345678");// 客服微信
                dataJson.put("telNo", "");// 联系电话
                dataJson.put("domainName", "http://www.zzyicheng.cn");// 域名
                dataJson.put("contactTel", "联系电话:");// 联系信息
                dataJson.put("icpNo", "京ICP备17048902号");// 备案信息

            } else if (appId.equals("2")) {
                resultJson.put("code", 0);
                resultJson.put("msg", "接口调用成功!");
                dataJson.put("contract", "这里是用户协议........");// 用户协议文本
                dataJson.put("appId", appId);// 参数回传
                dataJson.put("picPath", "https://c0001-1.zzyicheng.cn/vue/static/img/background.c7e07de7.jpg");// 背景图片
                dataJson.put("contactMsg", "客服微信:openjweb");// 联系信息
                dataJson.put("contactTel", "联系电话:18600000000");// 联系信息
                dataJson.put("comName", "北京众智益成科技有限公司");
                dataJson.put("sysName", (sysName == null || sysName.trim().length() == 0) ? "微商城系统" : sysName);

                dataJson.put("showSinaButton", "N");// 是否显示新浪按钮，是为Y N 为否 默认显示
                dataJson.put("showWeixinButton", "Y");// 是否显示微信按钮，是为Y N 为否 默认显示
                dataJson.put("showQQButton", "N");// 是否显示QQ按钮，是为Y N 为否 默认显示
                dataJson.put("sinaQRImage", "");// 新浪二维码扫码图片
                dataJson.put("qqQRImage", "");// QQ二维码扫码图片
                dataJson.put("weixinQRImage", "");// 微信二维码扫码图片
                dataJson.put("sinaLoginUrl", "");// 新浪登录链接（备用）
                dataJson.put("qqLoginUrl", "");// QQ登录链接（备用）
                dataJson.put("weixinLoginUrl", "");// 微信登录链接（备用）
                dataJson.put("logoPath", logoPath);// 登录页图标链接
                dataJson.put("contactTel", "联系电话:");// 联系信息
                dataJson.put("serviceWeixin", "12345678");// 客服微信
                dataJson.put("telNo", "");// 联系电话
                dataJson.put("domainName", "http://www.zzyicheng.cn");// 域名
                dataJson.put("icpNo", "京ICP备17048902号");

            } else { // 其他
                resultJson.put("code", 0);
                resultJson.put("msg", "接口调用成功!");
                dataJson.put("contract", "这里是用户协议........");// 用户协议文本
                dataJson.put("appId", appId);// 参数回传
                dataJson.put("picPath", "https://c0001-1.zzyicheng.cn/vue/static/img/background.c7e07de7.jpg");// 背景图片
                dataJson.put("contactMsg", "客服微信:openjweb");// 联系信息
                dataJson.put("contactTel", "联系电话:18600000000");// 联系信息
                dataJson.put("comName", "北京众智益成科技有限公司");
                dataJson.put("sysName", (sysName == null || sysName.trim().length() == 0) ? "业务管理平台" : sysName);

                dataJson.put("showSinaButton", "Y");// 是否显示新浪按钮，是为Y N 为否 默认显示
                dataJson.put("showWeixinButton", "Y");// 是否显示微信按钮，是为Y N 为否 默认显示
                dataJson.put("showQQButton", "Y");// 是否显示QQ按钮，是为Y N 为否 默认显示
                dataJson.put("sinaQRImage", "");// 新浪二维码扫码图片
                dataJson.put("qqQRImage", "");// QQ二维码扫码图片
                dataJson.put("weixinQRImage", "");// 微信二维码扫码图片
                dataJson.put("sinaLoginUrl", "");// 新浪登录链接（备用）
                dataJson.put("qqLoginUrl", "");// QQ登录链接（备用）
                dataJson.put("weixinLoginUrl", "");// 微信登录链接（备用）
                dataJson.put("logoPath", logoPath);// 登录页图标链接
                dataJson.put("contactTel", "联系电话:");// 联系信息
                dataJson.put("serviceWeixin", "12345678");// 客服微信
                dataJson.put("telNo", "");// 联系电话
                dataJson.put("domainName", "http://www.zzyicheng.cn");// 域名
                dataJson.put("icpNo", "京ICP备17048902号");

                // 其他
                resultJson.put("code", 0);
                resultJson.put("msg", "接口调用成功!");
            }
            resultJson.put("data", dataJson);

            return resultJson;

        }
    }
}
