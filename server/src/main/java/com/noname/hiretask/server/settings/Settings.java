package com.noname.hiretask.server.settings;

/**
 * Holds settings which are used to start the app.
 */
public class Settings {
    private final int port;
    private final String dataFolder;
    private final int procCount;

    public Settings(int port, String data, int procCount) {
        this.port = port;
        this.dataFolder = data;
        this.procCount = procCount;
    }

    public int getPort() {
        return port;
    }

    public String getDataFolder() {
        return dataFolder;
    }

    public int getProcCount() {
        return procCount;
    }
}
