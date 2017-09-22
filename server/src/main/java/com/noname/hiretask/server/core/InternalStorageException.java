package com.noname.hiretask.server.core;

/**
 * Thrown when it is failed to perform an operation on in-memory storage.
 */
public class InternalStorageException extends Exception {

    public InternalStorageException() {
    }

    public InternalStorageException(String message) {
        super(message);
    }
}
