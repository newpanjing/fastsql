package com.qikenet.fastsql.build;

import com.qikenet.fastsql.core.ObjectProxy;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

/**
 * 构建对象
 */
public class ObjectBuilder {


    public static  <T> T create(Class clazz, DataSource dataSource) {

        ObjectProxy proxy = new ObjectProxy(clazz);

        return  (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxy );
    }


}
