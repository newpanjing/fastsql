package org.fastsql.utils;

import org.apache.log4j.Logger;
import org.fastsql.annotation.Column;
import org.fastsql.exception.ConvertException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * 对象转换
 */
public class ConvertObject {

    private static final Logger logger = Logger.getLogger(ConvertObject.class);

    private Class beanClass;

    private Map<String, Field> fieldMapper;

    private Class actualType;

    public ConvertObject(Class beanClass, Class actualType) {
        this.beanClass = beanClass;
        this.actualType = actualType;
        init();
    }

    /**
     * 结果集转换到对象
     *
     * @param rs
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getResults(ResultSet rs, Class type) throws Exception {

        //用arraylist 防止乱序
        List results;

        if (type.isInterface()) {
            results = new ArrayList<>();
        } else {
            results = (List) type.newInstance();
        }

        while (rs.next()) {
            Map map = convertToMap(rs, actualType);
            results.add(map);
        }
        return results;
    }

    /**
     * 扫描对象注解信息
     */
    private void init() {
        fieldMapper = new HashMap<>();
        Field[] fields;
        if (actualType != null) {
            fields = actualType.getDeclaredFields();
        } else {
            fields = beanClass.getDeclaredFields();
        }
        for (Field field : fields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            String fieldName = field.getName();
            if (column != null) {
                fieldName = column.value();
            }
            fieldMapper.put(fieldName, field);
        }
    }

    /**
     * list map转换到对象
     *
     * @param results
     * @return
     * @throws Exception
     */
    public List<Object> convertListToObjects(List<Map<String, Object>> results) throws Exception {
        List<Object> resultList = new ArrayList<>();

        for (Map<String, Object> item : results) {
            resultList.add(convertMapToObject(item));
        }

        return resultList;
    }

    /**
     * map到对象，根据对象 注解设置
     *
     * @param item
     * @return
     */
    public Object convertMapToObject(Map<String, Object> item) throws Exception {

        Class objectClass = beanClass;
        //如果类型是map和list 特殊处理

        //是map 直接返回
        for (Class clazz : beanClass.getInterfaces()) {

            if (clazz == Map.class) {
                return item;
            }

            if (ClassUtils.isCollection(clazz)) {
                if (actualType != null) {
                    objectClass = actualType;
                    break;
                } else {
                    return item;
                }
            }
        }

        //扫描对象，映射
        Object bean = objectClass.newInstance();

        Set<String> keys = item.keySet();
        for (String label : keys) {
            setField(bean, label, item.get(label));
        }

        return bean;
    }

    /**
     * 对象赋值
     *
     * @param bean
     * @param label
     * @param value
     */
    private void setField(Object bean, String label, Object value) {
        Field field = fieldMapper.get(label);
        if (field != null) {
            if (value != null) {
                try {
                    Object val = value;

                    //忽略数据库类型，直接转为Java的8大基础类型
                    if (ClassUtils.isPrimitive(field.getType())) {
                        val = ClassUtils.convertPrimitive(field.getType(), value);
                    }
                    //其他不支持类型，直接设置，String、Date 等

                    field.set(bean, val);
                } catch (IllegalAccessException e) {
                    logger.error(e);
                    throw new ConvertException(e);
                }
            }
        }
    }

    /**
     * rs 转换到list
     *
     * @param rs
     * @return
     * @throws Exception
     */
    public List<Object> getList(ResultSet rs, Class type) throws Exception {

        List<Object> resultList;
        if (type.isInterface()) {
            resultList = new ArrayList<>();
        } else {
            resultList = (List<Object>) type.newInstance();
        }

        while (rs.next()) {
            Object bean = getBean(actualType, rs, rs.getMetaData());
            resultList.add(bean);
        }

        return resultList;
    }


    /**
     * rs 转换到对象
     *
     * @param rs
     * @return
     * @throws Exception
     */
    public Object getObject(ResultSet rs) throws Exception {

        if (rs.next()) {
            return getBean(beanClass, rs, rs.getMetaData());
        }

        return null;

    }

    /**
     * 获取一个对象
     *
     * @param rs
     * @param rsmd
     * @return
     * @throws Exception
     */
    private Object getBean(Class type, ResultSet rs, ResultSetMetaData rsmd) throws Exception {

        Object bean = type.newInstance();
        int count = rsmd.getColumnCount();
        //获取
        for (int i = 1; i <= count; i++) {
            String label = rsmd.getColumnLabel(i);
            setField(bean, label, rs.getObject(i));
        }

        return bean;
    }

    /**
     * 获取基础类型集合
     *
     * @param rs
     * @return
     * @throws Exception
     */
    public List<Object> getListPrimitive(ResultSet rs) throws Exception {
        List<Object> results = new ArrayList<>();

        Class type = actualType;

        veryColumns(rs);

        while (rs.next()) {

            Object value = null;
            if (type == null) {
                value = rs.getObject(0);
            } else {
                value = getPrimitive(actualType, rs);
            }
            results.add(value);

        }

        return results;
    }

    /**
     * 验证是否只有一列
     *
     * @param rs
     */
    private void veryColumns(ResultSet rs) {

        int count;
        try {
            count = rs.getMetaData().getColumnCount();
        } catch (Exception e) {
            throw new ConvertException(e);
        }
        //大于1列 报错
        if (count > 1) {
            throw new ConvertException(" column count is " + count + ", can not convert to primitive type.");
        }
    }

    /**
     * 获取单个基础类型
     *
     * @param rs
     * @return
     * @throws Exception
     */
    public Object getSimplePrimitive(ResultSet rs) throws Exception {

        //如果是未指定，默认为第1个字段的类型
        veryColumns(rs);

        if (rs.next()) {

            if (actualType == null) {
                Object value = rs.getObject(1);
                return value;

            }

            return getPrimitive(actualType, rs);
        }

        return null;
    }

    /**
     * 获取基础的值
     *
     * @param type
     * @param rs
     * @return
     */
    private Object getPrimitive(Class type, ResultSet rs) throws Exception {

        Object value = rs.getObject(1);
        if (type == String.class) {
            return value != null ? value.toString() : value;
        }

        return ClassUtils.convertPrimitive(type, value);

    }

    /**
     * 获取String
     *
     * @param rs
     * @return
     * @throws Exception
     */
    public Object getString(ResultSet rs) throws Exception {
        veryColumns(rs);
        if (rs.next()) {
            return getPrimitive(beanClass, rs);
        }
        return null;
    }

    private Map convertToMap(ResultSet rs, Class resultType) throws Exception {
        Map map = new LinkedHashMap();


        if (resultType != null && !resultType.isInterface()) {
            map = (Map) resultType.newInstance();
        }


        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();

        for (int i = 1; i <= count; i++) {
            map.put(rsmd.getColumnLabel(i), rs.getObject(i));
        }


        return map;
    }

    /**
     * 获取map
     *
     * @param rs
     * @param resultType
     * @return
     * @throws Exception
     */
    public Map getMap(ResultSet rs, Class resultType) throws Exception {

        Map result = null;
        if (rs.next()) {
            result = convertToMap(rs, resultType);
        }

        if (rs.next()) {
            //抛异常，无法将多个值转为一个map
            throw new ConvertException("Multiple values cannot be converted to a Map");
        }

        return result;
    }
}
