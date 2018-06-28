package org.fastsql.test;

import org.fastsql.annotation.*;

import java.util.*;

public interface UserDao {

    @Select("select *,age as 123age from user where auto_id=#{userId} and local=#{local}")
    public User findOne(@Param("userId") Long userId, @Param("remark") String remark, @Param("local") int local);

    @Select("select * from user")
    public ArrayList<User> selectAll();


    @Select("select * from user where auto_id=1")
    public Map<String, Object> getMap();

    @Select("select * from user")
    public Set<?> getSet();

    @Select("select * from user")
    public Set<User> getUserSet();

    @Select("select auto_id from user")
    public Set<Long> getUserIds();

    @Select("select name from user where auto_id=#{autoId}")
    public String getUserName(@Param("autoId") int autoId);

    @Select("select auto_id from user where name='${name}'")
    public long getIdByName(@Param("name") String name);

    @Select("select auto_id from user where auto_id=#{autoId}")
    public List<Long> getIds(@Param("autoId") Long autoId);

    @Update("update user set age=#{age} where auto_id=#{id}")
    public Void update(@Param("id") Long id, @Param("age") int age);

    @Update("delete from user where auto_id=#{id}")
    public int delete(@Param("id") Long id);

    @Select("select * from user")
    public LinkedList<User> selectAllUser();

    @Select("select * from user")
    public HashMap<String, Object> getHashMap();

    @Select("select * from user")
    public Set<TreeMap<String, Object>> getSetTreeMap();


    /**
     * 入参为对象，支持多级取值，#{user.a.b.c}
     * @param user
     * @return
     */
    @Insert("insert into user(name,sex,age,remark,local,create_time)values(#{user.name},#{user.sex},#{user.age},#{remark.text},#{user.local},#{user.createTime})")
    public int insert(@Param("user") User user,@Param("remark") Remark remark);


    /**
     * 不加@Param 注解，只支持一个参数
     * @param user
     * @return
     */
    @SelectKey(keyProperty = "id")
    @Insert("insert into user(name,sex,age,remark,local,create_time)values(#{name},#{sex},#{age},#{remark},#{local},#{createTime})")
    public int insertUser(User user);


}
