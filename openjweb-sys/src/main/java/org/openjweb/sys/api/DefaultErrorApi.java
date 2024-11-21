package org.openjweb.sys.api;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.exception.GlobalException;
import org.openjweb.common.exception.GlobalJsonException;
import org.openjweb.sys.entity.CommUser;
//import org.springframework.retry.annotation.Backoff;
//import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试：http://localhost:8001/demo/error/testError?flag=1
 * http://localhost:8001/demo/error/testError?flag=2
 *  *
 */
@RestController
@RequestMapping("/demo/error")
@Slf4j
public class DefaultErrorApi {

    @RequestMapping("testError")
    public CommUser testError(String flag){
        CommUser user = new CommUser();

        if("1".equals(flag)) {
            throw new GlobalException("演示全局异常", -1);
        }
        else {
            user.setRealName("张三");
            user.setLoginId("admin");
            return user;
        }
    }

    @RequestMapping("testJsonError")
    public CommUser testJsonError(String flag){
        CommUser user = new CommUser();

        if("1".equals(flag)) {
            throw new GlobalJsonException("演示全局异常", -1);
        }
        else {
            user.setRealName("张三");
            user.setLoginId("admin");

        }
        return user;
    }

    //http://localhost:8001/demo/error/testError2?flag=1
    //这个未拦截的异常会出现白页面
    @RequestMapping("testError2")
    public CommUser testError2(String flag) {
        int i=1/0;//未拦截异常
        CommUser user = new CommUser();
        user.setRealName("张三");
        user.setLoginId("admin");

        return user;
    }

    //@Retryable(value= {GlobalJsonException.class},maxAttempts=5,backoff=@Backoff(delay=5000,multiplier=2))
    //http://localhost:8001/demo/error/testError3?flag=1
    //这个未拦截的异常会出现白页面
    @RequestMapping("testError3")
    public CommUser testError3(String flag) {
        log.info("测试失败重试!!!!!");
        if("1".equals(flag)) {

            throw new GlobalJsonException("测试失败重试");
        }

        CommUser user = new CommUser();
        user.setRealName("张三");
        user.setLoginId("admin");

        return user;
    }


}
