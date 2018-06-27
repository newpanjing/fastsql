package org.fastsql.handler;

public interface ResultHandler {

    /**
     * 获取sql crud返回结果
     * @return
     */
    public Object handler() throws Exception;
}

