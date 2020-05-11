package com.qr.dbcontroller.exceptions;

public class NotFoundDataExceptions extends Exception {

    private static final String PREFIX_MESSAGE = "Не найдены данные в сущености ";

    protected String context;

    public NotFoundDataExceptions(String message) {
        this(PREFIX_MESSAGE + message, null);
    }

    public NotFoundDataExceptions(String message, Object context) {
        super(message);
        if (context != null) {
            this.context = context.toString();
        }

    }

}
