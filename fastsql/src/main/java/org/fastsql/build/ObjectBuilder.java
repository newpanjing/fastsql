package org.fastsql.build;

import org.fastsql.core.ObjectProxy;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

/**
 * 构建对象
 */
public class ObjectBuilder {


    public static  <T> T create(Class clazz, DataSource dataSource) {

        ObjectProxy proxy = new ObjectProxy(clazz);
        proxy.setDataSource(dataSource);
        return  (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxy );
    }


}
