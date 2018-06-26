package org.fastsql.test;

import com.qikenet.fastsql.annotation.*;
import org.fastsql.annotation.Param;
import org.fastsql.annotation.Select;

import java.util.ArrayList;

public interface UserDao {

    @Select("select *,age as 123age from user where auto_id=#{userId} and local=#{local}")
    public User findOne(@Param("userId") Long userId, @Param("remark") String remark, @Param("local") int local);

    @Select("select * from user")
    public ArrayList<User> selectAll();

}
