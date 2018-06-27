package org.fastsql.core;

import org.fastsql.build.SQLBase;
import org.fastsql.entity.SQLExecuteType;

public class SQLParameter extends SQLBase {


    private Class resultType;

    private SQLExecuteType type;

    private Class actualType;

    public Class getActualType() {
        return actualType;
    }

    public void setActualType(Class actualType) {
        this.actualType = actualType;
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
        return "SQLParameter{" +
                "resultType=" + resultType +
                "} " + super.toString();
    }
}
