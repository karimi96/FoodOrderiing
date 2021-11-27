package com.example.foodorderiing.helper;

public class Tools {
    public static Integer convertToPrice(String value){
        return Integer.valueOf(value.replace(".","")
                .replace(",","")
                .replace(" ",""));
    }
}
