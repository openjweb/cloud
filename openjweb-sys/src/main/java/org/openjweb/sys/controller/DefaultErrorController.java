package org.openjweb.sys.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class DefaultErrorController extends BasicErrorController{
    @Autowired
    public DefaultErrorController(ErrorAttributes errorAttributes,ServerProperties serverProperties,List<ErrorViewResolver> errorViewREsolvers) {
        super(errorAttributes,serverProperties.getError(),errorViewREsolvers);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request,HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String,Object> map =  getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML));
        response.setStatus(status.value());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            log.info(key+":"+String.valueOf(value));//timestamp,status,error,path
        }
        ModelAndView modelAndView = new ModelAndView("errorPage2",map,status);
        //下面可以实现方法将异常信息保存到数据库中
        return modelAndView;

    }

    @Override
    public ResponseEntity<Map<String,Object>> error(HttpServletRequest request){
        Map<String,Object> body =  getErrorAttributes(request,getErrorAttributeOptions(request, MediaType.ALL));
        body.put("custommsg","自定义错误信息!!!");
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(body,status);
    }

}
