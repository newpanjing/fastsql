package com.qikenet.fastsql.exception;

public class SQLBuildRuntimeException extends RuntimeException {

    public SQLBuildRuntimeException() {
    }

    public SQLBuildRuntimeException(String message) {
        super(message);
    }

    public SQLBuildRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLBuildRuntimeException(Throwable cause) {
        super(cause);
    }

    public SQLBuildRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
