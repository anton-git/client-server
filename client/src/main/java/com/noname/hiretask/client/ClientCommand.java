package com.noname.hiretask.client;

import java.util.Arrays;

/**
 * Enum contains all allowed client app arguments (commands)
 */
public enum ClientCommand {

    SERVER_PORT("-serverPort"),
    ADD_BIRD("-addbird"),
    ADD_SIGHTING("-addsighting"),
    LIST_BIRDS("-listbirds"),
    LIST_SIGHTINGS("-listsightings"),
    REMOVE("-remove"),
    QUIT("-quit");

    private String value;

    ClientCommand(String value) {
        this.value = value;
    }

    public static ClientCommand value(final String value) {
        return Arrays.stream(ClientCommand.values())
                     .filter(c -> c.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Not allowed value: " + value));
    }

    public String getValue() {
        return value;
    }
}
