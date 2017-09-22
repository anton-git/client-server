package com.noname.hiretask.client.command.executor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.noname.hiretask.client.util.Converter;
import com.noname.hiretask.client.command.executor.CommandExecutor;
import com.noname.hiretask.common.ProtocolCommand;
import com.noname.hiretask.common.RequestMessage;
import com.noname.hiretask.common.ResponseMessage;
import com.noname.hiretask.common.model.Bird;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.noname.hiretask.client.util.ConsolePrinterHelper.printBirds;

public class ListAllBirdsExecutor implements CommandExecutor {

    private final static TypeReference<List<Bird>> TYPE_REFERENCE = new TypeReference<List<Bird>>() {
    };

    @Override
    public RequestMessage prepareMessageForServer() {
        return new RequestMessage(ProtocolCommand.GET_ALL_BIRDS, "");
    }


    @Override
    public void onSuccess(ResponseMessage response) {
        final List<Bird> birds = Converter.covertTo(response.getBody(), TYPE_REFERENCE);
        final List<Bird> sortedBirds = birds.stream()
                                            .sorted(Comparator.comparing(Bird::getName))
                                            .collect(Collectors.toList());
        printBirds(sortedBirds);
    }

    @Override
    public String getMessageOnServerFailure() {
        return "Failed to get list of birds.";
    }


}
