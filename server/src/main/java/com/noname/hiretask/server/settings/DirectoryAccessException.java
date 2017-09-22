package com.noname.hiretask.server.settings;

/**
 * Thrown when directory is not accessible by the app
 */
public class DirectoryAccessException extends Exception {

    public DirectoryAccessException() {
    }

    public DirectoryAccessException(String message) {
        super(message);
    }
}
