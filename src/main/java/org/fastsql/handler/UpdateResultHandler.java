package org.fastsql.handler;

import org.fastsql.core.SQLParameter;
import org.fastsql.exception.ConvertException;
import org.fastsql.utils.ClassUtils;

public class UpdateResultHandler implements ResultHandler {

    private int number;
    private SQLParameter parameter;

    public UpdateResultHandler(int number, SQLParameter parameter) {
        this.number = number;
        this.parameter = parameter;
    }

    @Override
    public Object handler() throws Exception {

        //受影响行数
        Class resultType = parameter.getResultType();

        if (!ClassUtils.isPrimitive(resultType)) {
            throw new ConvertException(String.format("can not 'number' convert to '%s'", resultType));
        }

        return ClassUtils.convertPrimitive(resultType, number);
    }
}
