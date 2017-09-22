package com.noname.hiretask.client.command.executor.impl;

import com.noname.hiretask.client.command.executor.CommandExecutor;
import com.noname.hiretask.client.util.Converter;
import com.noname.hiretask.common.ProtocolCommand;
import com.noname.hiretask.common.RequestMessage;
import com.noname.hiretask.common.dto.TextRequestHolder;

import static com.noname.hiretask.client.util.UserConsoleUtil.getCorrectStringValue;
import static com.noname.hiretask.client.util.UserConsoleUtil.println;

public class RemoveExecutor implements CommandExecutor {

    private String birdName;

    @Override
    public RequestMessage prepareMessageForServer() {
        return new RequestMessage(ProtocolCommand.REMOVE_BIRD, getUserInput());
    }

    @Override
    public String getMessageOnServerSuccess() {
        return "Bird " + getBirdName() + " successfully removed from the database.";
    }

    @Override
    public String getMessageOnServerFailure() {
        return "Failed to remove bird " + getBirdName() + " from database.";
    }

    private String getUserInput() {
        println("Please, enter bird name.");
        final String name = getCorrectStringValue("Name");
        setBirdName(name);
        return Converter.toJsonString(new TextRequestHolder(name));
    }

    public String getBirdName() {
        return birdName;
    }

    public void setBirdName(String birdName) {
        this.birdName = birdName;
    }
}
