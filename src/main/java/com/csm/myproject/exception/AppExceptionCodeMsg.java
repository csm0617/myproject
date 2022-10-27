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
    INPUT_INVALID(10001,"请检查输入类型是否有误"),
    DELETE_ERR_MSG(10002,"删除失败"),
    UPDATE_ERR_MSG(10003,"修改失败"),
    INSERT_ERR_MSG(10004,"新增失败"),
    UPLOAD_AVATAR_ERR_MSG(10005,"修改失败"),
    USER_ALREADY_EXISTS_MSG(100006,"用户名重复"),
    ROLE_ALREADY_EXISTS_MSG(10007,"角色已存在！"),
    MENU_ALREADY_EXISTS_MSG(10008,"菜单已存在！");

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
