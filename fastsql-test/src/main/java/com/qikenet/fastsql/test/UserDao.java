package com.qikenet.fastsql.test;

import com.qikenet.fastsql.annotation.*;

import java.util.ArrayList;
import java.util.List;

public interface UserDao {

    @Select("select *,age as 123age from user where auto_id=#{userId} and local=#{local}")
    public User findOne(@Param("userId") Long userId, @Param("remark") String remark,@Param("local") int local);

    @Select("select * from user")
    public ArrayList<User> selectAll();

}
