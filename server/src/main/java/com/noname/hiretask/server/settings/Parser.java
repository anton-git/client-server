package com.noname.hiretask.server.settings;

/**
 * Interface for parser of arguments passed to the app.
 */
public interface Parser {

    /**
     * Minimum port number value
     */
    int MIN_PORT_NUMBER = 1;

    /**
     * Maximum port number value
     */
    int MAX_PORT_NUMBER = 65535;

    /**
     * Minimum count of threads which handle requests
     */
    int MIN_THREADS_COUNT_NUMBER = 1;

    /**
     * Maximum count of threads which handle requests
     */
    int MAX_TREAD_COUNT_NUMBER = 100;

    /**
     * Default port number
     */
    int DEFAULT_PORT = 3000;

    /**
     * Default directory where file with data is stored
     */
    String DEFAULT_FOLDER = System.getProperty("user.home") + System.getProperty("file.separator") + "serverdata";

    /**
     * Default size of thread pool which handles client requests
     */
    int DEFAULT_PROC_COUNT = 2;

    /**
     * App argument name for setting the port
     */
    String ARG_PORT = "-port";

    /**
     * App argument name for setting folder where data is stored
     */
    String ARG_DATA = "-data";

    /**
     * App argument name for setting a size of the thread pool for handling client requests
     */
    String ARG_PROC_COUNT = "-proc_count";

    /**
     * Method parses app arguments and returns {@link Settings} object with stored setting.
     * If some arguments are missing default values are used.
     *
     * @param args app arguments to parse
     * @return Settings object with parsed arguments stored in it
     * @throws InitializationParameterException if arguments are incorrect
     */
    Settings parse(String[] args) throws InitializationParameterException;
}
