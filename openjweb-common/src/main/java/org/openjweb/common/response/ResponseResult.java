package org.openjweb.common.response;


import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 通用的结果返回类
 * @param <T>
 */
public class ResponseResult<T> implements Serializable {

    private Integer code;
    private String msg="操作成功";

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    private Long count;
    private T data;

    public ResponseResult() {
        this.code = 0;
    }


    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = (T)new HashMap();
    }

    public static ResponseResult errorResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.error(code, msg);
    }

    public static ResponseResult okResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.ok(code, new HashMap<>(), msg);
    }
    public static ResponseResult okResult(Object data) {
        ResponseResult result = new ResponseResult();
        return result.ok(data);
    }
    ////okResult(count, list, 0,"查询成功!");
    public static ResponseResult okResult( Object  data, int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.ok(    data,  code,  msg);
    }

    public ResponseResult<?> ok( T data, int code, String msg) {

        ResponseResult result = new ResponseResult();

        result.code = code;
        result.msg = msg ;

        if(data instanceof IPage){
            result.setCount( ((IPage<?>) data).getTotal());
            result.setData( (T)((IPage)data) .getRecords());




        }
        else{
            System.out.println("count is:::sssssss");
            result.setData(data);


        }

       // this.msg = msg;
        return result;
    }




    public ResponseResult<?> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = (T) new HashMap();
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data, String msg) {
        this.code = code;

        this.msg = msg;
        this.data = data;

        return this;
    }


    public ResponseResult<?> ok(T data) {
        this.data = data;
        return this;
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
