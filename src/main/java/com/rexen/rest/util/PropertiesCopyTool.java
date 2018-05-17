package com.rexen.rest.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author: GavinHacker
 * @description: 用于不同结构对象拷贝
 * @date: Created in 下午3:59 18/3/9
 * @modifiedBy:
 */
public class PropertiesCopyTool {

    static Logger logger = Logger.getLogger(PropertiesCopyTool.class);

    public static Object copyProperties(JSONObject object, Class<?> targetCls, Map<String, String> nameMapping, Map<String , Class<?>> typeMapping) throws IllegalAccessException, InstantiationException {

        StringBuilder stringBuilder = new StringBuilder();
        Object target2 = targetCls.newInstance();
        object.forEach((x, y) -> {
            if (y instanceof JSONObject) {
                if (typeMapping.get(x) != null) {
                    try {
                        Object tempObj = copyProperties((JSONObject) y, typeMapping.get(x), nameMapping, typeMapping);
                        String orDefault = nameMapping.getOrDefault(x, x);
                        try {
                            setupFieldValue(target2, orDefault, tempObj, false);
                        } catch (Exception e) {
                            stringBuilder.append(String.format(" [Set field value exception: %s, code 0] ", e.getMessage()));
                        }
                    } catch (IllegalAccessException e) {
                        stringBuilder.append(String.format(" [IllegalAccessException %s, code 0] ", e.getMessage()));
                    } catch (InstantiationException e) {
                        stringBuilder.append(String.format(" [InstantiationException %s, code 0] ", e.getMessage()));
                    }
                }
            }else if(y instanceof JSONArray) {
                if (typeMapping.get(x) != null) {
                    JSONArray jsonArray = (JSONArray)y;
                    List<Object> list = new ArrayList<>();
                    jsonArray.stream().forEach(z -> {
                        try {
                            Object tempObj = copyProperties((JSONObject) z, typeMapping.get(x), nameMapping, typeMapping);
                            list.add(tempObj);
                        } catch (IllegalAccessException e) {
                            stringBuilder.append(String.format(" [IllegalAccessException %s, code 1] ", e.getMessage()));
                        } catch (InstantiationException e) {
                            stringBuilder.append(String.format(" [InstantiationException %s, code 1] ", e.getMessage()));
                        }
                    });
                    String orDefault = nameMapping.getOrDefault(x, x);
                    try {
                        setupFieldValue(target2, orDefault, list, false);
                    } catch (Exception e) {
                        stringBuilder.append(String.format(" [Set field value exception: %s, code 1] ", e.getMessage()));
                    }
                }
            }else{
                String orDefault = nameMapping.getOrDefault(x,x);
                try {
                    setupFieldValue(target2, orDefault, y, false);
                } catch (Exception e) {
                    try {
                        setupFieldValue(target2, orDefault, y, true);
                    } catch (NoSuchFieldException e1) {
                        stringBuilder.append(String.format(" [NoSuchFieldException: %s, code 0] ", e1.getMessage()));
                    } catch (IllegalAccessException e2) {
                        stringBuilder.append(String.format(" [IllegalAccessException: %s, code 0] ", e2.getMessage()));
                    }
                }
            }
        });
        if(stringBuilder.length() != 0){
            logger.debug(String.format("Copy jsonObject to ResourcePOJO errors: %s", stringBuilder.toString()));
        }
        return target2;
    }

    public static void setupFieldValue(Object target2, String x, Object y, boolean getSuper) throws IllegalAccessException, NoSuchFieldException {
        Field f = getSuper ? target2.getClass().getSuperclass().getDeclaredField(x) : target2.getClass().getDeclaredField(x);
        f.setAccessible(true);
        if(f == null || y == null){
            return;
        }
        if(f.getType().equals(y.getClass()) || f.getType().isAssignableFrom(y.getClass())) {
            f.set(target2, y);
        }else if(f.getType().equals(Date.class) && y.getClass().equals(Long.class)){
            Date d = new Date(Long.valueOf(y.toString()));
            f.set(target2, d);
        }
    }
}