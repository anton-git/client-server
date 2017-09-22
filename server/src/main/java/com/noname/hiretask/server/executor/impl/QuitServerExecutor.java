package com.noname.hiretask.server.executor.impl;

import com.noname.hiretask.common.ResponseMessage;
import com.noname.hiretask.server.executor.Executor;
import com.noname.hiretask.server.executor.RequestValidationException;

import java.net.Socket;
import java.util.function.Consumer;

/**
 * Responsible initiating server shutdown, but actually it is just sends a response to client
 * and real shutdown request is handled in {@link com.noname.hiretask.server.core.ClientRequestHandler} class.
 */
public class QuitServerExecutor implements Executor {
    @Override
    public ResponseMessage execute(String body) throws RequestValidationException {
        return new ResponseMessage(ResponseMessage.ResponseCode.OK, "Server quit initiated.");
    }
}
