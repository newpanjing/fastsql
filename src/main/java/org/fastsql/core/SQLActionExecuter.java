package org.fastsql.core;

import org.apache.log4j.Logger;
import org.fastsql.handler.InsertResultHandler;
import org.fastsql.handler.ResultHandler;
import org.fastsql.handler.SelectResultHandler;
import org.fastsql.handler.UpdateResultHandler;

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
        int number=0;

        switch (parameter.getType()) {
            case SELECT:
                ResultSet rs = ps.executeQuery();
                handler = new SelectResultHandler(rs, parameter);
                break;

            case INSERT:
                number = ps.executeUpdate();
                handler = new InsertResultHandler(number,parameter);
                break;
            case UPDATE:
            case DELETE:
                number = ps.executeUpdate();
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
