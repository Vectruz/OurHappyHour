package com.ourhappyhour.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {
    
    public static String toJson(Object object) {
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create();
        
        return gson.toJson(object);
    }
    
    public static <T extends Object> T fromJson(String json, Class<T> classOfT) {
    	Gson gson = new GsonBuilder()
    		.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")//.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        	.create();        
        
        return (T) gson.fromJson(json, classOfT);
    }

}
