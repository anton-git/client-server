package com.noname.hiretask.client.settings;

import java.time.format.DateTimeFormatter;

/**
 * Global static app settings are stored here.
 */
public class GlobalSettings {

    /**
     * Date time formatter with predefined format. It is used to parse date-time and to print it
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Width of table column when the table is printed in the console, e.g. when printing list of birds.
     */
    public static final int CONSOLE_TABLE_COLUMN_WIDTH = 20;

    /**
     * Default port number which is used if a user did not specified port number on the app start.
     */
    public static final int DEFAULT_PORT_NUMBER = 3000;

    /**
     * Default host of the server app.
     */
    public static final String DEFAULT_HOST = "127.0.0.1";

    /**
     * Timeout before a client stops waiting a response from a server.
     */
    public static final int SOCKET_TIMEOUT_IN_MILLISECONDS = 10 * 1000;

    /**
     * Default charset name
     */
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

}
