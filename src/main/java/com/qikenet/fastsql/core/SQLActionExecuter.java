package com.qikenet.fastsql.core;

import com.qikenet.fastsql.entity.SQLExecuteType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLActionExecuter {

    public static <T> T execute(Connection connection, SQLExecute execute) throws SQLException {


        Statement statement = connection.createStatement();

        //判断查询类型，update,select
        if (execute.getType() == SQLExecuteType.SELECT) {
            ResultSet rs = statement.executeQuery(execute.getSql());
            connection.close();
            while (rs.next()) {

                //结果集到对象

            }
        } else {

        }


        return null;
    }
}
