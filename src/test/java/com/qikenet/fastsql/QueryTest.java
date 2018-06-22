package com.qikenet.fastsql;


import com.alibaba.druid.pool.DruidDataSource;
import com.qikenet.fastsql.core.DefaultObjectFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class QueryTest {

    public static void main(String[] args) throws NoSuchMethodException, SQLException {

        DefaultObjectFactory factory = new DefaultObjectFactory();
        factory.setDataSource(getDataSource());
        UserDao userDao= factory.getBean(UserDao.class);

        userDao.insert("123", 321,11);


    }

    public static DataSource getDataSource() throws SQLException {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:sqlserver://192.168.19.244:1433;databaseName=DJ_rfdb;applicationIntent=ReadWrite;failoverPartner=DJ_rfdb");
        dataSource.setUsername("hddev");
        dataSource.setPassword("Hdpass101$");
        dataSource.setInitialSize(10);
        dataSource.setMaxActive(20);

        dataSource.init();

        return dataSource;
    }


}
