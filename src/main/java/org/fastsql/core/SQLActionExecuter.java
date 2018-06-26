package org.fastsql.core;

import org.fastsql.entity.SQLExecuteType;
import org.fastsql.utils.ClassUtils;
import org.fastsql.utils.ConvertObject;
import org.fastsql.utils.ConvertResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class SQLActionExecuter {

    public static Object execute(Connection connection, SQLExecute execute) throws Exception {


        PreparedStatement ps = connection.prepareStatement(execute.getSql());
        System.out.println(execute);
        //设置参数
        for (int i = 0; i < execute.getParameters().size(); i++) {
            Entry entry = execute.getParameters().get(i);
            Object value = entry.getValue();
            ps.setObject(i + 1, value);
        }
        Class resultType = execute.getResultType();

        //判断查询类型，select
        if (execute.getType() == SQLExecuteType.SELECT) {
            ResultSet rs = ps.executeQuery();
            //结果集到对象
            List<Map<String, Object>> results = ConvertResultSet.getResults(rs);
            ConvertObject convert = new ConvertObject(resultType,execute.getActualTypeArguments());
            List<?> list=convert.convertListToObjects(results);
            //如果不是集合，默认返回一条
            if (!ClassUtils.isCollection(execute.getResultType())) {
                //或者模仿mybatis报错
                return list.get(0);
            }
            return list;

        } else if (execute.getType() == SQLExecuteType.DELETE || execute.getType() == SQLExecuteType.UPDATE) {

            int number = ps.executeUpdate(execute.getSql());
            //受影响行数

        } else if (execute.getType() == SQLExecuteType.INSERT) {

            //执行update 和select key


        }
        connection.close();

        return null;
    }


}
