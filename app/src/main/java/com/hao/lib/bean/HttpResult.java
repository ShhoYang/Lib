package com.hao.lib.bean;

/**
 * @author Yang Shihao
 */

public class HttpResult<D> {

    private String reason;
    private Result result;
    private int error_code;

    public class Result {

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public boolean isOk() {
        return 0 == error_code;
    }
}