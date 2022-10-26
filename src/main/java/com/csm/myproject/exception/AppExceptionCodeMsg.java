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
    INPUT_INVALID(10001,"请检查菜单类型是否有误"),
    DELETE_ERR_MSG(10002,"删除失败"),
    UPDATE_ERR_MSG(10003,"修改失败"),
    UPLOAD_AVATAR_ERR_MSG(10004,"修改失败");

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
