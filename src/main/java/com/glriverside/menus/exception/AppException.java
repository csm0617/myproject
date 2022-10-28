package com.glriverside.menus.exception;
import lombok.Data;

/**
 * @Author 快乐小柴
 * @Date 2022/10/26 11:55
 * @Version 1.0
 */
@Data
public class AppException extends RuntimeException{
    private int code = 500;
    private String message = "服务器异常";


    public AppException(com.glriverside.menus.exception.AppExceptionCodeMsg appExceptionCodeMsg){
        super();
        this.code = appExceptionCodeMsg.getCode();
        this.message= appExceptionCodeMsg.getMessage();

    }

    public AppException(int code,String message){
        super();
        this.code = code;
        this.message = message;

    }

}
