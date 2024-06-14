package com.example.ocean2.exception;


public class ResourceTypeUsingException extends RuntimeException{

    public ResourceTypeUsingException(){
        super("The Resource Type To Be Deleted Is Still In Use");
    }
}
