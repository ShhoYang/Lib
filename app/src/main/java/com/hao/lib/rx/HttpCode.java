package com.hao.lib.rx;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yang Shihao
 */
public enum HttpCode {

    CODE_10001("10001", "错误的请求KEY"),
    CODE_10002("10002", "该KEY无请求权限"),
    CODE_10003("10003", "KEY过期"),
    CODE_10004("10004", "错误的OPENID"),
    CODE_10005("10005", "应用未审核超时,请提交认证"),
    CODE_10007("10007", "未知的请求源"),
    CODE_10008("10008", "被禁止的IP"),
    CODE_10009("10009", "被禁止的KEY"),
    CODE_10011("10011", "当前IP请求超过限制"),
    CODE_10012("10012", "请求超过次数限制"),
    CODE_10013("10013", "测试KEY超过请求限制"),
    CODE_10014("10014", "系统内部异常"),
    CODE_10020("10020", "接口维护"),
    CODE_10021("10021", "接口停用"),
    CODE_10031("10031", "网络异常");

    static Map<String, String> mMap = new HashMap<>();

    private String mCode;
    private String mMsg;

    HttpCode(String code, String msg) {
        mCode = code;
        mMsg = msg;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public static String getMsg(String code) {
        String msg = mMap.get(code);
        return msg == null ? "" : msg;
    }

    static {
        for (HttpCode httpCode : HttpCode.values()) {
            mMap.put(httpCode.getCode(), httpCode.getMsg());
        }
    }
}
