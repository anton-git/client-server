package com.noname.hiretask.server.executor.impl;

import com.noname.hiretask.common.ResponseMessage;
import com.noname.hiretask.common.dto.SightingFilterHolder;
import com.noname.hiretask.common.model.Sighting;
import com.noname.hiretask.server.converter.JsonConverter;
import com.noname.hiretask.server.core.InMemoryStorage;
import com.noname.hiretask.server.executor.Executor;
import com.noname.hiretask.server.executor.RequestValidationException;

import java.io.IOException;
import java.util.List;

/**
 * Responsible for getting list of {@link Sighting}s from the system
 */
public class ListSightingsExecutor implements Executor {

    @Override
    public ResponseMessage execute(String body) throws RequestValidationException, IOException {
        validateNonEmptyBody(body);
        final SightingFilterHolder filterHolder = JsonConverter.fromJson(body, SightingFilterHolder.class);
        final List<Sighting> sightings = InMemoryStorage.getInstance().getSightingsForBird(filterHolder);
        return new ResponseMessage(ResponseMessage.ResponseCode.OK, JsonConverter.toJson(sightings));
    }
}
