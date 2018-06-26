package org.fastsql.test;


import com.alibaba.druid.pool.DruidDataSource;
import org.fastsql.core.DefaultObjectFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class QueryTest {

    public static void main(String[] args) throws NoSuchMethodException, SQLException {

        DefaultObjectFactory factory = new DefaultObjectFactory();
        factory.setDataSource(getDataSource());
        UserDao userDao= factory.getBean(UserDao.class);

        User user= userDao.findOne( 1l, "222",1);
        System.out.println(user);

        List<User> userList = userDao.selectAll();
        System.out.println(userList);
    }

    public static DataSource getDataSource() throws SQLException {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        dataSource.setInitialSize(10);
        dataSource.setMaxActive(20);

        dataSource.init();

        return dataSource;
    }


}
