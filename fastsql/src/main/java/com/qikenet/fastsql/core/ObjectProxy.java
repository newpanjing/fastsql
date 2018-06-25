package com.qikenet.fastsql.core;

import com.qikenet.fastsql.build.SQLBuilder;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

public class ObjectProxy implements InvocationHandler {

    private Class clazz;

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ObjectProxy(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        SQLExecute execute = SQLBuilder.builder(clazz, method, args);
        System.out.println(execute);

        return null;
    }


}
