package com.noname.hiretask.server.executor;

import com.noname.hiretask.common.ResponseMessage;

import java.io.IOException;

/**
 * Interface defining executors of client requests - objects which execute particular client request e.g. add bird to a storage.
 */
public interface Executor {

    /**
     * Executes an operation requested by a client and returns {@link ResponseMessage} with status of the operation and its result.
     *
     * @param body body of client request, e.g. bird data to add.
     * @return {@link ResponseMessage} with status of the operation and its result
     * @throws RequestValidationException
     * @throws IOException
     */
    ResponseMessage execute(String body) throws RequestValidationException, IOException;

    /**
     * Default method which validates that body of a client request is not empty
     * @param body Body of a client request
     * @throws RequestValidationException
     */
    default void validateNonEmptyBody(final String body) throws RequestValidationException {
        if (body == null || body.trim().isEmpty()) {
            throw new RequestValidationException("Request body is empty.");
        }
    }
}
