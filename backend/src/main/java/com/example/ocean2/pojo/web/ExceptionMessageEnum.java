package com.example.ocean2.pojo.web;



public enum ExceptionMessageEnum {
    URL_NOT_FOUND("URL '%s' Not Found"),
    METHOD_UNSURPORT("Request method '%s' is not supported"),
    UNKNOWN_EXCEPTION("Unknown Exception");

    private String msg;
    ExceptionMessageEnum(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
