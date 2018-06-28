package org.fastsql.handler;

import org.fastsql.core.SQLParameter;
import org.fastsql.utils.ClassUtils;
import org.fastsql.utils.ConvertObject;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectResultHandler implements ResultHandler {

    private ResultSet rs;
    private SQLParameter execute;

    private Class resultType;
    private Class actualType;

    public SelectResultHandler(ResultSet rs, SQLParameter parameter) {
        this.rs = rs;
        this.execute = parameter;
        this.resultType = parameter.getResultType();
        this.actualType = parameter.getActualType();
    }

    @Override
    public Object handler() throws Exception {


        //结果集到对象

        Object result;
        ConvertObject convert = new ConvertObject(resultType, execute.getActualType());

        //判断返回的是集合还是对象
        //是对象，需要判断记录条数<=1,>1 报错，=0 返回null


        if (ClassUtils.isCollection(resultType)) {
            result = handlerCollection(convert);
        } else if (ClassUtils.isMap(resultType)) {
            //处理map<object>
            result = convert.getMap(rs,resultType);

        } else if (ClassUtils.isPrimitive(resultType)) {
            //处理返回单个基本类型
            result = convert.getSimplePrimitive(rs);
        }else if(ClassUtils.isString(resultType)){
            //处理返回类型为String，String特殊，不算基本类型
            result = convert.getString(rs);
        } else {
            //处理返回单个对象
            result = convert.getObject(rs);
        }

        return result;
    }

    private Set handlerSet(List list) throws IllegalAccessException, InstantiationException {
        Set set;
        if(resultType.isInterface()) {
            set = new HashSet<>();
        }else{
            set =(Set) resultType.newInstance();
        }

        set.addAll(list);
        return set;
    }

    private Object handlerCollection(ConvertObject convert) throws Exception {

        Object result;
        //list 基础类型 特殊处理

        //泛型为null，未指定，默认为list<map>
        if (actualType == null) {

            //处理set<?> 和list<?>
            //判断set和list

            List<?> list = convert.getResults(rs,resultType);
            if (ClassUtils.isSet(resultType)) {

                result = handlerSet(list);

            }else{
                result = list;
            }

        } else if (ClassUtils.isPrimitive(actualType)) {
            //处理list<基础类型>
            result = convert.getListPrimitive(rs);

        } else if (ClassUtils.isMap(actualType)) {
            //处理list<map>
            result = convert.getResults(rs,resultType);
            if (ClassUtils.isSet(resultType)) {
                result = handlerSet((List)result);
            }
        } else if (ClassUtils.isSet(resultType)) { //set 特殊处理
            //默认返回HashSet

            //处理set<bean>
            List<?> list = convert.getList(rs,resultType);
            result = handlerSet(list);

        } else {
            //处理list<bean>
            result = convert.getList(rs,resultType);
        }

        return result;
    }
}
