package cn.raccoon.team.boot.exception;

/**
 * @author Qian
 */

@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum EmError implements CommonError {


    PLEASE_TO_LOGIN(10005, "请先登录"),

    PASSWD_ACCOUNT_ERROR(10004, "账号密码错误"),

    USER_NOT_FOUND(10003, "用户不存在"),

    LOGIN_EXPIRED(10002, "登录过期"),

    UNKNOWN_ERROR(10001, "未知异常");

    private int errCode;
    private String errMsg;

    private EmError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
