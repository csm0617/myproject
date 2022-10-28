package com.glriverside.menus.exception;

import com.glriverside.menus.exception.AppException;
import com.glriverside.menus.response.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author 快乐小柴
 * @Date 2022/10/26 11:53
 * @Version 1.0
 * 自定义异常拦截器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public <T> Response<T> exceptionHandler(Exception e){
        //这里先判断拦截到的Exception是不是我们自定义的异常类型
        if(e instanceof AppException){
            AppException appException = (AppException)e;
            return Response.error(appException.getCode(),appException.getMessage());
        }

        //如果拦截的异常不是我们自定义的异常(例如：数据库主键冲突)
        //return Response.error(500,"服务器端异常");
        return Response.error(500,e.toString());
    }
}
