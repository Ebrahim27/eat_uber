package com.ebi.snap_food.utils;

public class SecurityUtils {
    public String createSMSCode() {
        //Introducing commons Lang package
        int randomPin = (int) (Math.random() * 90000) + 10000;
        String code = String.valueOf(randomPin);
        //return "54321";
        return code;
    }

}