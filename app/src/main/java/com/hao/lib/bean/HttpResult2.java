package com.hao.lib.bean;

/**
 * @author Yang Shihao
 */

public class HttpResult2<D> {

    private String reason;
    private int error_code;
    private D result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public D getResult() {
        return result;
    }

    public void setResult(D result) {
        this.result = result;
    }
}