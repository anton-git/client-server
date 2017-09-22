package com.noname.hiretask.server.executor;

/**
 * Thrown if a request from a client is invalid, e.g. body is empty but required.
 */
public class RequestValidationException extends Exception {

    public RequestValidationException() {
    }

    public RequestValidationException(String message) {
        super(message);
    }

    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
