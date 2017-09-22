package com.noname.hiretask.server.executor.impl;

import com.noname.hiretask.common.ResponseMessage;
import com.noname.hiretask.common.model.Sighting;
import com.noname.hiretask.server.converter.JsonConverter;
import com.noname.hiretask.server.core.InMemoryStorage;
import com.noname.hiretask.server.core.InternalStorageException;
import com.noname.hiretask.server.executor.Executor;
import com.noname.hiretask.server.executor.RequestValidationException;

import java.io.IOException;

/**
 * Responsible for adding a {@link Sighting} to the system.
 */
public class AddSightingExecutor implements Executor {


    @Override
    public ResponseMessage execute(String body) throws RequestValidationException, IOException {
        validateNonEmptyBody(body);

        final Sighting newSighting = JsonConverter.fromJson(body, Sighting.class);

        try {
            InMemoryStorage.getInstance().addSighting(newSighting);
            return new ResponseMessage(ResponseMessage.ResponseCode.OK, "");
        } catch (InternalStorageException e) {
            return new ResponseMessage(ResponseMessage.ResponseCode.FAILED, e.getMessage());
        }

    }
}
