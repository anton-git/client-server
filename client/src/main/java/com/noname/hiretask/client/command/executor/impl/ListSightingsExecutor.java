package com.noname.hiretask.client.command.executor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.noname.hiretask.client.command.executor.CommandExecutor;
import com.noname.hiretask.client.util.Converter;
import com.noname.hiretask.common.ProtocolCommand;
import com.noname.hiretask.common.RequestMessage;
import com.noname.hiretask.common.ResponseMessage;
import com.noname.hiretask.common.dto.SightingFilterHolder;
import com.noname.hiretask.common.model.Sighting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.noname.hiretask.client.util.ConsolePrinterHelper.printSightings;
import static com.noname.hiretask.client.util.UserConsoleUtil.*;

public class ListSightingsExecutor implements CommandExecutor {

    private static final Logger log = LoggerFactory.getLogger(ListSightingsExecutor.class);

    private final static TypeReference<List<Sighting>> TYPE_REFERENCE = new TypeReference<List<Sighting>>() {
    };

    @Override
    public RequestMessage prepareMessageForServer() {
        return new RequestMessage(ProtocolCommand.GET_SIGHTINGS, getUserInput());
    }

    @Override
    public void onSuccess(ResponseMessage response) {
        log.debug("Starting on success operation.");
        doOnSuccessResponse(response);
        log.debug("Finished on success operation.");
    }

    private void doOnSuccessResponse(ResponseMessage response) {
        final List<Sighting> sightings = Converter.covertTo(response.getBody(), TYPE_REFERENCE);
        final Comparator<Sighting> sightingComparator = Comparator.comparing(Sighting::getBird)
                                                                  .thenComparing(Sighting::getTime);
        final List<Sighting> sortedSightings = sightings
                .stream()
                .sorted(sightingComparator)
                .collect(Collectors.toList());

        printSightings(sortedSightings);
    }


    private String getUserInput() {
        println("Please, enter sightings filter information.");

        final String name = getCorrectStringValue("Bird name (regexp allowed)");
        final LocalDateTime startDate = getCorrectDateTimeValue("Start date");
        final LocalDateTime endDate = getCorrectDateTimeValue("End date");

        return Converter.toJsonString(SightingFilterHolder.of(name, startDate, endDate));
    }

}
