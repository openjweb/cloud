package org.openjweb.sys.api;

import lombok.extern.slf4j.Slf4j;
import org.openjweb.common.exception.GlobalException;
import org.openjweb.common.exception.GlobalJsonException;
import org.openjweb.sys.entity.CommUser;
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

}
