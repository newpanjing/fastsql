package com.qikenet.fastsql.core;

import com.qikenet.fastsql.build.SQLBase;
import com.qikenet.fastsql.entity.SQLExecuteType;

public class SQLExecute extends SQLBase {


    private Class resultType;

    private SQLExecuteType type;

    public SQLExecuteType getType() {
        return type;
    }

    public void setType(SQLExecuteType type) {
        this.type = type;
    }

    public Class getResultType() {
        return resultType;
    }

    public void setResultType(Class resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "SQLExecute{" +
                "resultType=" + resultType +
                "} " + super.toString();
    }
}
