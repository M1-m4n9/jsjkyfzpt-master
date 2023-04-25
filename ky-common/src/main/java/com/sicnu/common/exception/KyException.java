package com.sicnu.common.exception;


/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 13:01
 */

public class KyException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String message;
    private int code = 500;

    public KyException(){
        this.message = "出现问题";
    }
    public KyException(String message){
        this.message = message;
    }
    public KyException(String message,int code){
        this.message = message;
        this.code = code;
    }
    public KyException(CodeEnume codeEnume){
        this.message = codeEnume.getMessage();
        this.code = codeEnume.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
