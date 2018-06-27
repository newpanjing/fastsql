package org.fastsql.core;

import org.apache.log4j.Logger;
import org.fastsql.entity.SQLExecuteType;
import org.fastsql.exception.ConvertException;
import org.fastsql.handler.ResultHandler;
import org.fastsql.handler.SelectResultHandler;
import org.fastsql.handler.UpdateResultHandler;
import org.fastsql.utils.ClassUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLActionExecuter {

    private final static Logger logger = Logger.getLogger(SQLActionExecuter.class);

    public static Object execute(Connection connection, SQLParameter parameter) throws Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("SQL: " + parameter.getSql());
            logger.debug("SQL type: " + parameter.getType());
            logger.debug("SQL parameters: " + parameter.getParameters());
        }

        PreparedStatement ps = connection.prepareStatement(parameter.getSql());
        //设置参数
        for (int i = 0; i < parameter.getParameters().size(); i++) {
            Entry entry = parameter.getParameters().get(i);
            Object value = entry.getValue();
            ps.setObject(i + 1, value);
        }


        ResultHandler handler = null;

        //判断查询类型
        switch (parameter.getType()) {
            case SELECT:
                ResultSet rs = ps.executeQuery();
                handler = new SelectResultHandler(rs, parameter);
                break;

            case INSERT:

                break;
            case UPDATE:
            case DELETE:
                int number = ps.executeUpdate();
                //受影响行数
                handler = new UpdateResultHandler(number, parameter);
                break;

        }

        if (handler != null) {
            return handler.handler();
        }

        connection.close();

        return null;
    }


}
