# fastsql

fastsql 是一个数据库访问框架
***Maven依赖***
<pre>
<code>
<p>
    <br/>
</p>
<p>
    &lt;dependency&gt;
</p>
<p>
    &nbsp; &nbsp; &lt;groupId&gt;org.fastsql&lt;/groupId&gt;
</p>
<p>
    &nbsp; &nbsp; &lt;artifactId&gt;fastsql&lt;/artifactId&gt;
</p>
<p>
    &nbsp; &nbsp; &lt;version&gt;1.0&lt;/version&gt;
</p>
<p>
    &lt;/dependency&gt;
</p>
<p>
    <br/>
</p>
</code>
</pre>

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

**返回类型支持**
8个基础、String、Set、List、Map
默认：
Set=HashSet
List=ArrayList
Map=LinkedHashMap

Set和List 不指定泛型，默认为Set<Map<String,Object>>和List<Map<String,Object>>


