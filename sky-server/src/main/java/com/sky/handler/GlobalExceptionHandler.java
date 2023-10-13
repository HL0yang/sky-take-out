package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    //java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '李四' for key 'employee.idx_username'

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.info("异常信息：{}",ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            String message = ex.getMessage();
            String[] s = message.split(" ");
            String err = "用户"+s[2]+"已存在";
            return Result.error(err);
        }
        else {
            return Result.error("未知错误！");
        }
    }

}
