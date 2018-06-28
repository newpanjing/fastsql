package org.fastsql.utils;

import org.fastsql.exception.ConvertException;

import java.util.*;

public class ClassUtils {


    /**
     * 判断是否是set
     *
     * @param clazz
     * @return
     */
    public static boolean isSet(Class clazz) {
        if (clazz == Set.class) {
            return true;
        }
        boolean val = false;
        Class[] classes = clazz.getInterfaces();
        for (Class c : classes) {
            if (c == Set.class) {
                val = true;
            }

            if(isSet(c)){
                val=true;
                break;
            }
        }
        return val;
    }

    /**
     * 判断是否是集合
     *
     * @param clazz
     * @return
     */
    public static boolean isCollection(Class clazz) {
        if (clazz == Collection.class) {
            return true;
        }
        boolean val = false;
        if (clazz.isArray()) {
            val = true;
        } else {
            Class[] classes = clazz.getInterfaces();
            for (Class c : classes) {
                if (c == List.class || c == Collection.class) {
                    val = true;
                    break;
                }

                if(isCollection(c)){
                    val=true;
                    break;
                }
            }
        }


        return val;
    }

    /**
     * 判断是否是map
     *
     * @param clazz
     * @return
     */
    public static boolean isMap(Class clazz) {

        if (clazz == Map.class) {
            return true;
        }

        boolean val = false;
        Class[] classes = clazz.getInterfaces();
        for (Class c : classes) {
            if (c == Map.class) {
                val = true;
            }
            if(isMap(c)){
                val=true;
                break;
            }
        }
        return val;
    }

    /**
     * 判断是否是基础类型，包含基本类型和包装类型
     *
     * @param type
     * @return
     */
    public static boolean isPrimitive(Class type) {

        if (type.isPrimitive()) {
            return true;
        } else if (type == Boolean.class ||
                type == Character.class ||
                type == Byte.class ||
                type == Short.class ||
                type == Integer.class ||
                type == Long.class ||
                type == Float.class ||
                type == Double.class ||
                type == Void.class) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否是String
     * @param type
     * @return
     */
    public static boolean isString(Class type) {
        return type == String.class;
    }

    /**
     * 基础类型转行
     *
     * @param typeClass
     * @param value
     * @return
     * @see java.lang.Boolean#TYPE
     * @see java.lang.Character#TYPE
     * @see java.lang.Byte#TYPE
     * @see java.lang.Short#TYPE
     * @see java.lang.Integer#TYPE
     * @see java.lang.Long#TYPE
     * @see java.lang.Float#TYPE
     * @see java.lang.Double#TYPE
     */
    public static Object convertPrimitive(Class typeClass, Object value) {
        Object result = null;

        //如果不是基础类型 报错
        if (!isPrimitive(typeClass)) {
            throw new ConvertException(typeClass.getName() + " is not primitive");
        }


        if (value == null) {
            return null;
        }

        try {

            String val = value.toString();

            if (typeClass == Boolean.class || typeClass == boolean.class) {
                result = Boolean.valueOf(val);
            } else if (typeClass == Character.class || typeClass == char.class) {
                result = Character.valueOf(val.charAt(0));
            } else if (typeClass == Byte.class || typeClass == byte.class) {
                result = Byte.valueOf(val);
            } else if (typeClass == Short.class || typeClass == short.class) {
                result = Short.valueOf(val);
            } else if (typeClass == Integer.class || typeClass == int.class) {
                result = Integer.valueOf(val);
            } else if (typeClass == Long.class || typeClass == long.class) {
                result = Long.valueOf(val);
            } else if (typeClass == Float.class||typeClass==float.class) {
                result = Float.valueOf(val);
            } else if (typeClass == Double.class || typeClass == double.class) {
                result = Double.valueOf(val);
            }

        } catch (Exception e) {
            throw new ConvertException(e);
        }

        return result;

    }


    public static void main(String[] args) {
//        System.out.println(isCollection(ArrayList.class));
        System.out.println(isSet(TreeSet.class));
    }
}
