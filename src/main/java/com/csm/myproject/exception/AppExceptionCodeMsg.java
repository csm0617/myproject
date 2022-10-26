package com.csm.myproject.exception;

import lombok.Data;

/**
 * @Author 快乐小柴
 * @Date 2022/10/26 11:54
 * @Version 1.0
 */
//构造业务错误返回枚举类型
public enum AppExceptionCodeMsg {
    //枚举类型
    NOT_FIND_MENU(10000,"没有找到菜单"),
    INPUT_INVALID(10003,"请检查菜单类型是否有误"),
    USERNAME_NOT_EXISTS(10001,"用户名不存在"),
    USER_CREDIT_NOT_ENOUTH(10002,"用户积分不足");

    private int code ;
    private String message ;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    AppExceptionCodeMsg(int code, String message){
        this.code = code;
        this.message = message;
    }
}
