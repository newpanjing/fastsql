package org.fastsql.core;

import org.fastsql.build.ObjectBuilder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DefaultObjectFactory {

    private Map<Class, Object> objectCache;

    private DataSource dataSource;

    /**
     * 设置数据源
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DefaultObjectFactory() {
        objectCache = new HashMap<>();
    }

    public <T> T getBean(Class<T> beanClass) {

        return ObjectBuilder.create(beanClass,dataSource);
    }

}
