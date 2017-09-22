package com.noname.hiretask.server.executor.impl;

import com.noname.hiretask.common.ResponseMessage;
import com.noname.hiretask.common.model.Bird;
import com.noname.hiretask.server.converter.JsonConverter;
import com.noname.hiretask.server.core.InMemoryStorage;
import com.noname.hiretask.server.core.InternalStorageException;
import com.noname.hiretask.server.executor.Executor;
import com.noname.hiretask.server.executor.RequestValidationException;

import java.io.IOException;

/**
 * Responsible for adding a {@link Bird} to the system.
 */
public class AddBirdExecutor implements Executor {

    @Override
    public ResponseMessage execute(String body) throws RequestValidationException, IOException {
        validateNonEmptyBody(body);

        final Bird newBird = JsonConverter.fromJson(body, Bird.class);

        try {
            InMemoryStorage.getInstance().addBird(newBird);
            return new ResponseMessage(ResponseMessage.ResponseCode.OK, "");
        } catch (InternalStorageException e) {
            return new ResponseMessage(ResponseMessage.ResponseCode.FAILED, e.getMessage());
        }
    }
}
