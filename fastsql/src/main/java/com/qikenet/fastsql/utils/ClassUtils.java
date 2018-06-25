package com.qikenet.fastsql.utils;

import java.util.Collection;
import java.util.List;

public class ClassUtils {


    /**
     * 判断是否是集合
     * @param clazz
     * @return
     */
    public static boolean isCollection(Class clazz) {

        boolean val = false;
        if (clazz.isArray()) {
            val = true;
        }else{
            Class[] classes = clazz.getInterfaces();
            for (Class c : classes) {
                if (c == List.class || c == Collection.class) {
                    val = true;
                }
            }
        }


        return val;
    }

    public static void main(String[] args) {
        System.out.println(isCollection(List.class));
    }
}
