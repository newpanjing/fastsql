package org.fastsql.core;

import org.fastsql.build.SQLBuilder;

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
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {

        SQLParameter execute = SQLBuilder.builder(clazz, method, args);

        Connection connection = dataSource.getConnection();

        return SQLActionExecuter.execute(connection, execute);
    }


}
