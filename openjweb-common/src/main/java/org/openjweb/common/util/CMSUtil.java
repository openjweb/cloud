package org.openjweb.common.util;

import javax.servlet.http.HttpServletRequest;

public class CMSUtil {
    public static String getDomainName(HttpServletRequest request)  //返回当前请求的域名
    {
        String requestUrl = request.getRequestURL().toString();
        //logger.info("a::::"+requestUrl);
        requestUrl = requestUrl.replace("http://", "");//去掉头
        //logger.info("a1::::"+requestUrl);
        if(requestUrl.indexOf("/")>-1)
        {
            requestUrl = requestUrl.substring(0,requestUrl.indexOf("/"));
        }
        //logger.info("a2::::"+requestUrl);

        String[] tmpDomainArray = requestUrl.split(":");//过滤掉端口号
        requestUrl = tmpDomainArray[0];//获取域名
        //logger.info("a3::::"+requestUrl);
        return requestUrl;
    }
}
