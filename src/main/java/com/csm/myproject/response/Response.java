package com.csm.myproject.response;

import com.csm.myproject.exception.AppExceptionCodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 快乐小柴
 * @Date 2022/10/26 11:41
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response <T>{
    private Integer code;
    private String message;
    private T data;

    //构造一个成功返回，用来返回<boolean>和数据
    public static <T> Response ok(T data){
        Response response = new Response<>(200, "success", data);
        return response;
    }
    //构造附带信息和数据的返回
    public static <T> Response success(String message,T data){
        Response response = new Response(200,message, data);
        return response;
    }
    //错误返回：构造自定义异常返回
    public static <T> Response error(AppExceptionCodeMsg appExceptionCodeMsg){
        Response response = new Response<>(appExceptionCodeMsg.getCode(), appExceptionCodeMsg.getMessage(), null);
        return response;
    }
    //构造普通错误的返回
    public static <T> Response error(Integer code,String message){
        Response response = new Response<>(code,message, null);
        return response;
    }


}
