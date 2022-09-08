package com.example.reggiegzjtest.common;


import java.util.HashMap;

public class R<T> {
    public Integer code;
    public String msg;
    public T data;
    public HashMap hashMap = new HashMap();
    public static <T> R<T> success(T object){
        R<T> r = new R<>();
        r.code = 1;
        r.data = object;
        return r;
    }

    public static <T> R<T> error(String errorInfo){
        R<T> r = new R<T>();
        r.code = 0;
        r.msg = errorInfo;
        return r;
    }

    public R<T> add(String key,Object value){
        hashMap.put(key,value);
        return this;
    }
}
