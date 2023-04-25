package com.sicnu.common.exception;



public enum CodeEnume {
    
    //查询结果为空的枚举
    NULL_RESULT(20001,"查询结果为空"),

    // 异常类的枚举
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"数据校验格式错误"),
    FERIGN_PERSON_SERVICE_EXCEPTION(10002,"PersonService远程调用出错"),

    //登录问题的枚举
    USER_NULL(30001,"用户不存在"),
    USER_NOT_ACTIVE(30002,"用户被冻结"),
    USER_NOT_MATCH(30003,"用户账户或密码错误"),
    USER_ACCOUNT_TYPE_ERROR(30004,"账号类型错误"),
    USER_NO_ROLE(30005,"用户没有角色"),
    USER_AUTH_TOKEN_FILTER_ERROR(30006,"Auth中的TokenFilter认证错误"),
    USER_AUTH_TOKEN_VERIFY_FILTER_ERROR(30007,"Auth中的TokenVerifyFilter出错"),
    USER_NO_LOGINED(30008,"用户没有登录"),
    USER_NO_AUTHENTICATION(30009,"用户没有认证"),
    USER_NO_POWER(30010,"用户没有权限"),
    
    //个人中心错误
    FOLLOW_ERROR(50001, "不能关注自己"),

    //其它问题
    ANNOTATION_SAVEPATH_ERROR(40000,"SavePath注解出错"),
    VALIDCODE_EROOR_OR_NULL(40001,"验证码错误或为空"),
    DELETE_ROLE_FAIL(40002,"该角色有其他人被使用不能删除"),
    DELETE_POWER_FAIL(40003,"该权限有角色在使用不能删除");
    
    
    
    
    private int code;
    private String message;
    CodeEnume(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}
