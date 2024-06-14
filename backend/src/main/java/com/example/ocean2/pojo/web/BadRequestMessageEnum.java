package com.example.ocean2.pojo.web;

public enum BadRequestMessageEnum {
    //GET请求的某个参数不允许为空
    GET_PARAMETER_CANNOT_NULL("parameter cannot be null"),

    //GET请求的部分参数可以为空但不能同时为空
    GET_PARAMETER_ILLEGAL_NULL("parameters' null type illegal"),

    //POST请求的对象不合法
    POST_JSON_OBJECT_ILLEGAL("json object format illegal")

    ;

    private final String prefix = "Bad Request: ";
    private String msg;
    BadRequestMessageEnum(String msg){
        this.msg = prefix + msg;
    }

    public String getMsg() {
        return msg;
    }
}
