package org.openjweb.core.config;



import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.ext.web.WebVariable;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

public class I18nFunction implements Function {

    private WebApplicationContext wac;

    public I18nFunction(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public Object call(Object[] obj, Context context) {
        HttpServletRequest request = (HttpServletRequest) context.getGlobal(WebVariable.REQUEST);
        RequestContext requestContext = new RequestContext(request);
        String message = requestContext.getMessage((String) obj[0]);
        return message;
    }

}

