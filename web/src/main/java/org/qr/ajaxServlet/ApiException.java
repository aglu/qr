package org.qr.ajaxServlet;


public class ApiException extends Exception {

    protected String context;

    public ApiException(String message) {
        this(message, (Throwable) null);
    }

    public ApiException(String message, Boolean isDeleteSession) {
        this(message, (Throwable) null);
    }

    public ApiException(String message, Object context) {
        super(message);
        if (context != null) {
            this.context = context.toString();
        }

    }
}
