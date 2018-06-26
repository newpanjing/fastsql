# fastsql

fastsql 是一个数据库访问框架

***范例***

<pre>
public interface UserDao {
 
     @Select("select *,age as 123age from user where auto_id=#{userId} and local=#{local}")
     public User findOne(@Param("userId") Long userId, @Param("remark") String remark, @Param("local") int local);
 
     @Select("select * from user")
     public ArrayList<User> selectAll();
 
 }
</pre>
使用UserDao进行查询
<pre>
public class QueryTest {

    public static void main(String[] args) throws NoSuchMethodException, SQLException {

        //定义工厂
        DefaultObjectFactory factory = new DefaultObjectFactory();
        //设置数据源
        factory.setDataSource(getDataSource());
        //从工厂获取dao实例
        UserDao userDao= factory.getBean(UserDao.class);

        //执行查询，返回单个对象
        User user= userDao.findOne( 1l, "222",1);
        System.out.println(user);

        //返回集合
        List<User> userList = userDao.selectAll();
        System.out.println(userList);
    }

    public static DataSource getDataSource() throws SQLException {
        //测试使用alibaba druid数据源
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
</pre>