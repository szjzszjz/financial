package com.szjz.utils;

import net.sf.json.JSONObject;

/**
 * author:szjz
 * date:2019/6/22
 */
public class JsonUtil {

    /** 对象转json */
    public static String toJson(Object object){
        return JSONObject.fromObject(object).toString();
    }
}
