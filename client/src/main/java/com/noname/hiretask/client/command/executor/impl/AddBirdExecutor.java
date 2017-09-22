package com.noname.hiretask.client.command.executor.impl;

import com.noname.hiretask.client.command.executor.CommandExecutor;
import com.noname.hiretask.client.util.Converter;
import com.noname.hiretask.client.util.UserConsoleUtil;
import com.noname.hiretask.common.ProtocolCommand;
import com.noname.hiretask.common.RequestMessage;
import com.noname.hiretask.common.model.Bird;


public class AddBirdExecutor implements CommandExecutor {

    private String birdName;

    @Override
    public RequestMessage prepareMessageForServer() {
        return new RequestMessage(ProtocolCommand.ADD_BIRD, getUserInput());
    }

    @Override
    public String getMessageOnServerSuccess() {
        return "Bird " + getBirdName() + " successfully added to the database.";
    }

    @Override
    public String getMessageOnServerFailure() {
        return "Failed to add bird " + getBirdName() + " to the database";
    }

    private String getUserInput() {

        UserConsoleUtil.println("Please, enter bird information.");

        final String name = UserConsoleUtil.getCorrectStringValue("Name");
        setBirdName(name);
        final String color = UserConsoleUtil.getCorrectStringValue("Color");
        final int weight = UserConsoleUtil.getCorrectNumberValue("Weight");
        final int height = UserConsoleUtil.getCorrectNumberValue("Height");

        return Converter.toJsonString(new Bird(name, color, weight, height));
    }

    public String getBirdName() {
        return birdName;
    }

    public void setBirdName(String birdName) {
        this.birdName = birdName;
    }

}
