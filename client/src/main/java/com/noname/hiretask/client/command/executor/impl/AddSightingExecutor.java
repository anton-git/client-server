package com.noname.hiretask.client.command.executor.impl;

import com.noname.hiretask.client.command.executor.CommandExecutor;
import com.noname.hiretask.client.util.Converter;
import com.noname.hiretask.common.ProtocolCommand;
import com.noname.hiretask.common.RequestMessage;
import com.noname.hiretask.common.model.Sighting;

import java.time.LocalDateTime;

import static com.noname.hiretask.client.util.UserConsoleUtil.*;


public class AddSightingExecutor implements CommandExecutor {

    @Override
    public RequestMessage prepareMessageForServer() {
        return new RequestMessage(ProtocolCommand.ADD_SIGHTING, getUserInput());
    }

    @Override
    public String getMessageOnServerSuccess() {
        return "Sighting successfully added to the database.";
    }

    @Override
    public String getMessageOnServerFailure() {
        return "Failed to add sighting to the database.";
    }

    private String getUserInput() {
        println("Please, enter sighting information.");

        final String birdName = getCorrectStringValue("Bird Name");
        final String location = getCorrectStringValue("Location");
        final LocalDateTime dateTime = getCorrectDateTimeValue("Date time");

        return Converter.toJsonString(new Sighting(location, dateTime, birdName));
    }

}
