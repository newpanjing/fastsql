package com.qikenet.fastsql.core;

public class Entry {

    private String key;
    private Object value;

    public Entry(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Entry() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
