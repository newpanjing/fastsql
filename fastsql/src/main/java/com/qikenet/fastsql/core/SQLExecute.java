package com.qikenet.fastsql.core;

import com.qikenet.fastsql.build.SQLBase;
import com.qikenet.fastsql.entity.SQLExecuteType;

import java.lang.reflect.Type;

public class SQLExecute extends SQLBase {


    private Class resultType;

    private SQLExecuteType type;

    private Class actualTypeArguments;

    public Class getActualTypeArguments() {
        return actualTypeArguments;
    }

    public void setActualTypeArguments(Class actualTypeArguments) {
        this.actualTypeArguments = actualTypeArguments;
    }

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
