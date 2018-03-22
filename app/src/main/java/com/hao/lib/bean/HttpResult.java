package com.hao.lib.bean;

/**
 * @author Yang Shihao
 */

public class HttpResult<D> {

    private String stat;
    private D data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

}