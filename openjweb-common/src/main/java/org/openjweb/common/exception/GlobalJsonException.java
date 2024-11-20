package org.openjweb.common.exception;

import org.openjweb.common.constant.ResponseConst;

/**
 * 用于API调用时统一处理错误异常
 */

public class GlobalJsonException extends RuntimeException {

    private int code = ResponseConst.STATE_FAIL;//默认错误码

    private String appName;//异常产生的应用名称

    public GlobalJsonException(String msg) {
        super(msg);
    }

    public GlobalJsonException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public GlobalJsonException(String message, String appName) {
        super(message);
        this.appName = appName;
    }

    public GlobalJsonException(String message, int code, String appName) {
        super(message);
        this.code = code;
        this.appName = appName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
