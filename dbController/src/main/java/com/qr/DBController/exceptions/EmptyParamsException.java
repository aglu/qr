package com.qr.DBController.exceptions;

public class EmptyParamsException extends Exception {

    protected String context;

    private static final String PREFIX_MESSAGE = "Не заполнен обязательный параметр ";

    public EmptyParamsException(String message) {
        this(PREFIX_MESSAGE + message, (Throwable) null);
    }

    public EmptyParamsException(String message, Object context) {
        super(message);
        if (context != null) {
            this.context = context.toString();
        }

    }
}
