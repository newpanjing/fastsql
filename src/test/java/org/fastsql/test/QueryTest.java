package org.fastsql.test;


import com.alibaba.druid.pool.DruidDataSource;
import org.fastsql.annotation.Param;
import org.fastsql.core.DefaultObjectFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public class QueryTest {

    public static void main(String[] args) throws NoSuchMethodException, SQLException, IllegalAccessException, InstantiationException {

        DefaultObjectFactory factory = new DefaultObjectFactory();
        factory.setDataSource(getDataSource());
        UserDao userDao= factory.getBean(UserDao.class);

//        User user= userDao.findOne( 1l, "222",1);
//        System.out.println(user);

//        List<User> userList = userDao.selectAll();
//        System.out.println(userList);

//        Map<String, Object> map = userDao.getMap();
//        System.out.println(map);

//        List<Long> ids = userDao.getIds(1l);
//        System.out.println(ids);

//        userDao.update(1l, 20);

//        System.out.println(userDao.delete(1l));


//        List<Long> ids = userDao.getIds(1l);
//        System.out.println(ids);

//        Set<?> sets = userDao.getSet();
//        System.out.println(sets);

//        Long id = userDao.getIdByName("rt");
//        System.out.println(id);


//        String name = userDao.getUserName(1);
//        System.out.println(name);

//        LinkedList linkedList = userDao.selectAllUser();
//        System.out.println(linkedList);

//        Map<String, Object> user = userDao.getMap();
//        System.out.println(user);

        Set<TreeMap<String,Object>> mapSet=userDao.getSetTreeMap();
        System.out.println(mapSet);
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
