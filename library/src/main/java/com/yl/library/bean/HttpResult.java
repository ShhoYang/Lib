package com.yl.library.bean;

/**
 * @author Yang Shihao
 */

public class HttpResult<D> {

    private int code;
    private String desc;
    private D data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}