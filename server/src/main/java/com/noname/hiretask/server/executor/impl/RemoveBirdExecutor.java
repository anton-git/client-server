package com.noname.hiretask.server.executor.impl;

import com.noname.hiretask.common.ResponseMessage;
import com.noname.hiretask.common.dto.TextRequestHolder;
import com.noname.hiretask.server.converter.JsonConverter;
import com.noname.hiretask.server.core.InMemoryStorage;
import com.noname.hiretask.server.core.InternalStorageException;
import com.noname.hiretask.server.executor.Executor;
import com.noname.hiretask.server.executor.RequestValidationException;

import java.io.IOException;

/**
 * Responsible for removing a bird from the system.
 */
public class RemoveBirdExecutor implements Executor {

    @Override
    public ResponseMessage execute(String body) throws RequestValidationException, IOException {
        validateNonEmptyBody(body);
        final String birdName = JsonConverter.fromJson(body, TextRequestHolder.class).getRequest();
        try {
            InMemoryStorage.getInstance().removeBirdWithItsSightings(birdName);
            return new ResponseMessage(ResponseMessage.ResponseCode.OK, "");
        } catch (InternalStorageException e) {
            return new ResponseMessage(ResponseMessage.ResponseCode.FAILED, e.getMessage());
        }
    }
}
