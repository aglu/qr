package com.qr.dbcontroller.exceptions;

public class EmptyParamsException extends Exception {

    private static final String PREFIX_MESSAGE = "Не заполнен обязательный параметр ";
    protected String context;

    public EmptyParamsException(String message) {
        this(PREFIX_MESSAGE + message, null);
    }

    public EmptyParamsException(String message, Object context) {
        super(message);
        if (context != null) {
            this.context = context.toString();
        }

    }
}
