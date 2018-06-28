package org.fastsql.handler;

import org.fastsql.core.SQLParameter;

public class InsertResultHandler implements ResultHandler {

    private int number;
    private SQLParameter parameter;

    public InsertResultHandler(int number, SQLParameter parameter) {
        this.number = number;
        this.parameter = parameter;
    }

    @Override
    public Object handler() throws Exception {

        //插入后，返回影响行数
        //如果加了selectKey，返回key


        return null;
    }
}
