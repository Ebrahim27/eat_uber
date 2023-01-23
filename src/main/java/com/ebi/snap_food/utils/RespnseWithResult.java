package com.ebi.snap_food.utils;

public class RespnseWithResult extends   ErrorMessage{
    Object result ;

    public RespnseWithResult(int status, String message, Object result) {
        super(status, message);
        this.result=result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
