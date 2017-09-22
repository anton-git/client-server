package com.noname.hiretask.client.settings;

import com.noname.hiretask.client.ClientCommand;

/**
 * Parser of app settings which passed by a user as app arguments.
 */
public class AppSettingsParser {

    /**
     * Parses app arguments
     *
     * @param args arguments passed to the app
     * @return parsed app arguments stored into {@link ParametersHolder} object
     */
    public static ParametersHolder parseArguments(String[] args) {

        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("No parameters passed.");
        }
        final ClientCommand clientCommand = ClientCommand.value(args[0]);

        if (clientCommand.equals(ClientCommand.SERVER_PORT)) {
            if (args.length != 3) {
                throw new IllegalArgumentException("Incorrect parameters.");
            }
            return new ParametersHolder(parsePort(args[1]), ClientCommand.value(args[2]));
        }

        return new ParametersHolder(GlobalSettings.DEFAULT_PORT_NUMBER, clientCommand);
    }

    private static int parsePort(String arg) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException("port value can not be empty.");
        }

        final int value = Integer.parseInt(arg);
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException("Port allowed values from 0 to 65535.");
        }

        return value;
    }
}
