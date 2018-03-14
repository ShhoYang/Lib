package com.yl.library.rx;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yang Shihao
 */
public enum HttpCode {

    CODE_0("0", "操所成功"),
    CODE_10001("10001", "登录成功"),
    CODE_10002("10002", "注册成功"),
    CODE_20000("20000", "登录失败"),
    CODE_20001("20001", "操作失败"),
    CODE_20002("20002", "手机号已被注册"),
    CODE_20003("20003", "用户名或者密码错误"),
    CODE_20004("20004", "验证码错误"),
    CODE_20005("20005", "TOKEN错误或者已失效"),
    CODE_20006("20006", "参数错误"),
    CODE_20007("20007", "没有操作权限"),
    CODE_20008("20008", "注册失败"),
    CODE_20009("20009", "未知错误"),
    CODE_20012("20012", "该用户还未注册"),
    CODE_20013("20013", "未查询到相关数据"),
    CODE_20014("20014", "数据重复"),
    CODE_20015("20015", "数据未发生任何改变"),
    CODE_20016("20016", "操作校验未通过"),
    CODE_20017("20017", "用户名或者密码不能为空"),
    CODE_20018("20018", "会话已过期或者无效"),
    CODE_20019("20020", "该账户已被锁定,无法登陆,管理员解锁后方可再次登录"),
    CODE_20026("20026", "密码错误次数太多,账户已被锁定"),
    CODE_20027("20027", "提交失败,事项材料中有照片没有上传"),
    CODE_20028("20028", "提交失败,未找到下一步事件处理人"),
    CODE_30001("30001", "网络异常,请稍后重试"),
    CODE_30002("30002", "网络请求失败"),
    CODE_30003("30003", "空数据"),
    CODE_30004("30004", "获取监控播放地址失败");

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
