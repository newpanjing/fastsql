package org.fastsql.test;

import org.fastsql.annotation.Param;
import org.fastsql.annotation.Select;
import org.fastsql.annotation.Update;

import java.util.*;

public interface UserDao {

    @Select("select *,age as 123age from user where auto_id=#{userId} and local=#{local}")
    public User findOne(@Param("userId") Long userId, @Param("remark") String remark, @Param("local") int local);

    @Select("select * from user")
    public ArrayList<User> selectAll();


    @Select("select * from user")
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

}
