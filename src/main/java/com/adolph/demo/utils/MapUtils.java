package com.adolph.demo.utils;

import java.util.Map;

public class MapUtils {

    public static String getStringValue(Map map, String key){
        if(map==null) return  "";
        if(key==null) return  "";
        if(!map.containsKey(key)) return  "";
        String v = map.get(key)+"";
        if(v==null) return  "";
        if("null".equals(v)) return  "";
        return  v;
    }

    public static double getDoubleValue(Map map, String key){
        if(map==null) return  0d;
        if(key==null) return 0d;
        if(!map.containsKey(key)) return  0d;
        double  v = 0d;
        try {
            String t =  map.get(key)+"";
            v = Double.valueOf(t);
            return v;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
