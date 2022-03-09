package com.hurry.servicebase.exceptionhandler;

import com.hurry.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/2/20
 * To change this template use File | Settings | File Templates.
 * Description:统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //制订出现什么异常会执行这个方法，Exception表示所以异常都会执行
//    @ExceptionHandler(Exception.class)
//    @ResponseBody   //返回数据
//    public R error(Exception e){
//        e.printStackTrace();
//        return R.error().message("执行了全局异常处理...");
//    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody   //返回数据
    public R error(GuliException e){
        log.error(e.getMsg());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
