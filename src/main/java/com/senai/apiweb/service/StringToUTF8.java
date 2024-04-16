
package com.senai.apiweb.service;

import java.nio.charset.StandardCharsets;


public class StringToUTF8 {
    
    public static String strUTF8(String msg){
        
        if (msg != null){
            String rawString = msg;
            byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
            return new String(bytes, StandardCharsets.UTF_8);
        } else {
            return null;
        }        
    }
}