package org.openjweb.common.exception;

import org.openjweb.common.constant.ResponseConst;

public class GlobalException extends RuntimeException {

    private int code = ResponseConst.STATE_FAIL;//默认错误码

    private String appName;//异常产生的应用名称

    public GlobalException(String msg) {
        super(msg);
    }

    public GlobalException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public GlobalException(String message, String appName) {
        super(message);
        this.appName = appName;
    }

    public GlobalException(String message, int code, String appName) {
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
