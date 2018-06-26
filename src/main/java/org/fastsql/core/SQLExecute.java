package org.fastsql.core;

import org.fastsql.build.SQLBase;
import org.fastsql.entity.SQLExecuteType;

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
