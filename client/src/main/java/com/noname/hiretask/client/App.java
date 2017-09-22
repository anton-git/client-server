package com.noname.hiretask.client;


import com.noname.hiretask.client.command.ServerCommunicator;
import com.noname.hiretask.client.settings.AppSettingsParser;
import com.noname.hiretask.client.settings.ParametersHolder;
import com.noname.hiretask.client.util.UserConsoleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class where the app is started.
 */
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final String errorMessage =
            "Incorrect command.\nUsage: [-serverPort <integer>] {-addbird | -addsighting | -listbirds | -listsightings | -remove | -quit}";

    /**
     * App entry point.
     *
     * @param args arguments passed by a user into the app
     */
    public static void main(String[] args) {
        try {
            final ParametersHolder parametersHolder = AppSettingsParser.parseArguments(args);
            final ClientCommand clientCommand = parametersHolder.getClientCommand();
            final ServerCommunicator communicator = new ServerCommunicator(parametersHolder.getPort());

            UserConsoleUtil.println("Client started.");
            communicator.communicate(clientCommand);
            UserConsoleUtil.println("Client stopped.");

        } catch (IllegalArgumentException e) {
            UserConsoleUtil.println(errorMessage);
            log.error(e.getMessage(), e);
        }
    }

}
