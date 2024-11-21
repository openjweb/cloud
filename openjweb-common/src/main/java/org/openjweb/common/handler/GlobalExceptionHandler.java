package org.openjweb.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.exception.GlobalException;
import org.openjweb.common.exception.GlobalJsonException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice(basePackages= {"org.openjweb"})
public class GlobalExceptionHandler {

    @ExceptionHandler({GlobalException.class})
    @ResponseBody
    public ModelAndView globalErrorHandler(HttpServletRequest request,HttpServletResponse response,Exception ex) throws Exception {
        ModelAndView mv = new ModelAndView();
        if(true) {
            log.info("自定义全局异常 GlobalException 异常处理........");
            GlobalException thisException = (GlobalException)ex;
            int errorCode = thisException.getCode();
            mv.addObject("errorCode", errorCode);
            mv.addObject("errorMessage","GlobalException处理异常："+ex.getMessage());
            mv.addObject("requestUrl",request.getRequestURL().toString());
            mv.setViewName("errorPage");
        }
        return mv;

    }

    /**
     * 返回JSON格式的错误信息
     * @param request
     * @param response
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler({GlobalJsonException.class})
    @ResponseBody
    public JSONObject globalJsonErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        GlobalJsonException thisException = (GlobalJsonException)ex;
        JSONObject errJson = new JSONObject();
        errJson.put("code",thisException.getCode());
        errJson.put("msg",thisException.getMessage());
        return errJson;

    }

    /*@ExceptionHandler({Exception.class})
    @ResponseBody
    public JSONObject globalExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {

        JSONObject errJson = new JSONObject();
        errJson.put("code",-1);
        errJson.put("msg",ex.getMessage());
        return errJson;

    }*/

}
