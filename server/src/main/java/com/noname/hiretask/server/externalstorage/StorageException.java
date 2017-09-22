package com.noname.hiretask.server.externalstorage;

/**
 * Thrown when there were issues with storing data to a storage ol loading data from it.
 */
public class StorageException extends Exception {

    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
