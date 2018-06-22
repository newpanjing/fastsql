package com.qikenet.fastsql;

import com.qikenet.fastsql.annotation.*;
import com.qikenet.fastsql.entity.SQLExecuteType;

public interface UserDao {

    @Select("select * from user where user_id=#{userId} and name='${name}' and remark='${remark}'")
    public User findOne(@Param("userId") Long userId,@Param("name") String name,@Param("remark") String remark);


    @SelectKey
    @Insert("insert into user(name,age)values(#{name},#{age})")
    public Integer insert(@Param("name") String userName,@Param("age") Integer age,@Param("age1") Integer page);


    @SelectKey(keyProperty = "id",keyColumn = "id")
    @Insert("insert into user(name,age)values(#{name},#{age})")
    public void saveUser(User user);

    @SelectKey
    @Automatic(SQLExecuteType.INSERT)
    public Integer save(User user);


    @Automatic(SQLExecuteType.UPDATE)
    public Integer update(User user);

    @Automatic(SQLExecuteType.UPDATE)
    public Integer update(@Param("id") Long id);
}
