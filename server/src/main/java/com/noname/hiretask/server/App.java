package com.noname.hiretask.server;

import com.noname.hiretask.server.core.Server;
import com.noname.hiretask.server.settings.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class where the app is started.
 */
public class App {

    private static Logger log = LoggerFactory.getLogger(App.class );

    private final static String instruction = "Use any of the following options: \n" +
            "  -port <integer between 1 and 65535>] (Optional) \n" +
            "  -data <folder to store information> (Optional) \n" +
            "  -proc_count <positive integer> (Optional) \n";

    /**
     * App entry point.
     *
     * @param args
     */
    public static void main(String[] args) {

        Settings settings = null;
        final Parser parser = new AppArgumentsParser();

        try {
            settings = parser.parse(args);
        } catch (InitializationParameterException e) {
            log.error(e.getMessage());
            log.info(instruction);
            return;
        }

        final SettingsValidator settingsValidator = new SettingsValidator(settings);
        try {
            settingsValidator.validateDirectory();
            printSettings(settings);
        } catch (DirectoryAccessException e) {
            log.error(e.getMessage());
            return;
        }

        new Server(settings).start();
    }

    private static void printSettings(Settings settings) {
        log.info("Settings used");
        log.info("port: {}", settings.getPort());
        log.info("directory: {}", settings.getDataFolder());
        log.info("thread count: {}", settings.getProcCount());
    }

}
