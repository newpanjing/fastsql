package org.fastsql.build;

import org.fastsql.core.Entry;

import java.util.List;

public class SQLBase {

    private String sql;
    private List<Entry> parameters;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Entry> getParameters() {
        return parameters;
    }

    public void setParameters(List<Entry> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "SQLBase{" +
                "sql='" + sql + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
