package com.noname.hiretask.server.settings;

/**
 * Thrown when incorrect app arguments are passed to the app.
 */
public class InitializationParameterException extends Exception {

    public InitializationParameterException(String message) {
        super(message);
    }

    public InitializationParameterException(String parameterName, InitializationExceptionReason reason) {
        super("Failed to parse parameter " + parameterName + "; reason: " + reason);
    }
}
