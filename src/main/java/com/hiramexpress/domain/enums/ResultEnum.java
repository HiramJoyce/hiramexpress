package com.hiramexpress.domain.enums;

/**
 * @author caohailiang
 * 返回结果类型枚举
 * code规则：成功为1，失败为0，后续改为不同状态码对应不同的提示
 */
public enum  ResultEnum {
    SUCCESS(1, "成功"),
    ERROR(0, "失败"),
    NOT_LOGIN(0, "未登录"),
    LOGIN_EXPIRY(0, "登录失效，请重新登陆"),
    PERMISSION_DENIED(0, "没有权限"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
