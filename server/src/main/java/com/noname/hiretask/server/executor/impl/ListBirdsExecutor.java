package com.noname.hiretask.server.executor.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.noname.hiretask.common.ResponseMessage;
import com.noname.hiretask.common.model.Bird;
import com.noname.hiretask.server.converter.JsonConverter;
import com.noname.hiretask.server.core.InMemoryStorage;
import com.noname.hiretask.server.executor.Executor;
import com.noname.hiretask.server.executor.RequestValidationException;

import java.util.List;

/**
 * Responsible for getting list of {@link Bird}s from the system
 */
public class ListBirdsExecutor implements Executor {

    @Override
    public ResponseMessage execute(String body) throws RequestValidationException, JsonProcessingException {

        final List<Bird> birds = InMemoryStorage.getInstance().getAllBirds();
        return new ResponseMessage(ResponseMessage.ResponseCode.OK, JsonConverter.toJson(birds));
    }
}
