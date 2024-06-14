package com.example.ocean2.pojo.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    int code;
    String msg;
    Object obj;

    static public Result success(){
        return new Result(0,"success",null);
    }
    static public Result success(Object obj){
        return new Result(0,"success",obj);
    }

    static public Result failure(int code, String msg){
        return new Result(code,msg,null);
    }
}
