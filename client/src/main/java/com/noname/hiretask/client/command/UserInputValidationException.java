package com.noname.hiretask.client.command;

/**
 * Thrown if a data entered by a user is invalid.
 */
public class UserInputValidationException extends RuntimeException {
    public UserInputValidationException() {
    }

    public UserInputValidationException(String message) {
        super(message);
    }

    public UserInputValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
