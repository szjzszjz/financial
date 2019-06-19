package com.szjz.manager.exception;

import com.szjz.result.Result;
import com.szjz.result.ResultEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * author:szjz
 * date:2019/6/19
 * <p>
 * 统一异常处理
 */

@ControllerAdvice(basePackages = {"com.szjz.manager.controller"})
public class MyExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Result handlerException(Exception e) {
        String message = e.getMessage();
        ResultEnum resultEnum = ResultEnum.getEnumByMsg(message);
        if (resultEnum != null) {
            //存在定义的code
            return Result.fail(resultEnum);
        }else {
            //不存在定义的code
           return Result.fail(e.getMessage());
        }
    }
}
