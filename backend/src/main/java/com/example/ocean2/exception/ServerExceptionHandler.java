package com.example.ocean2.exception;


import com.example.ocean2.pojo.web.ExceptionMessageEnum;
import com.example.ocean2.pojo.web.Result;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ServerExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public  Result noResourceHandler(NoResourceFoundException e){
        e.printStackTrace();
        String resourcePath = e.getResourcePath();
        String msg = ExceptionMessageEnum.URL_NOT_FOUND.getMsg().formatted(resourcePath);
        return Result.failure(404,msg);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result requestMethodExceptionHandler(HttpRequestMethodNotSupportedException e){
        String method = e.getMethod();
        String msg = ExceptionMessageEnum.METHOD_UNSURPORT.getMsg().formatted(method);
        return Result.failure(400,msg);
    }

    @ExceptionHandler(ResourceTypeUsingException.class)
    public Result resourceTypeUsingExceptionHandler(ResourceTypeUsingException e){
        return Result.failure(400,e.getMessage());
    }

    @ExceptionHandler(ForeignKeyColumnMissException.class)
    public Result foreignKeyColumnMissExceptionHandler(ForeignKeyColumnMissException e){
        return Result.failure(400,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){
        e.printStackTrace();
        return Result.failure(500,ExceptionMessageEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
