package com.example.ocean2.exception;

public class ForeignKeyColumnMissException extends RuntimeException{
    public ForeignKeyColumnMissException(){
        super("Target Element Missing In Foreign Key Column");
    }
}
