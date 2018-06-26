package org.fastsql.utils;

import org.fastsql.annotation.Column;

import java.lang.reflect.Field;

import java.util.*;

public class ConvertObject {

    private Class beanClass;

    private Map<String, Field> fieldMapper;

    private Class actualTypeArguments;

    public ConvertObject(Class beanClass, Class actualTypeArguments) {
        this.beanClass = beanClass;
        this.actualTypeArguments = actualTypeArguments;
        init();
    }

    /**
     * ɨ�����ע����Ϣ
     */
    private void init() {
        fieldMapper = new HashMap<>();
        Field[] fields;
        if (actualTypeArguments != null) {
            fields = actualTypeArguments.getDeclaredFields();
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
     * list mapת��������
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
     * map�����󣬸��ݶ��� ע������
     *
     * @param item
     * @return
     */
    public Object convertMapToObject(Map<String, Object> item) throws Exception {

        Class objectClass = beanClass;
        //���������map��list ���⴦��

        //��map ֱ�ӷ���
        for (Class clazz : beanClass.getInterfaces()) {

            if (clazz == Map.class) {
                return item;
            }

            if (ClassUtils.isCollection(clazz)) {
                if (actualTypeArguments != null) {
                    objectClass = actualTypeArguments;
                    break;
                } else {
                    return item;
                }
            }
        }

        //ɨ�����ӳ��
        Object bean = objectClass.newInstance();


        Set<String> keys = item.keySet();
        for (String label : keys) {

            Field field = fieldMapper.get(label);

            if (field != null) {
                Object value = item.get(label);
                if (value != null) {
                    field.set(bean, value);
                }
            }


        }

        return bean;
    }


}
