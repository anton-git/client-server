package com.noname.hiretask.client.settings;

import com.noname.hiretask.client.ClientCommand;

/**
 * Holder for arguments passed by a user to client app on starting.
 */
public class ParametersHolder {

    /**
     * Server port number
     */
    private final int port;

    /**
     * Command that client should execute on server
     */
    private final ClientCommand clientCommand;

    public ParametersHolder(int port, ClientCommand clientCommand) {
        this.port = port;
        this.clientCommand = clientCommand;
    }

    public int getPort() {
        return port;
    }

    public ClientCommand getClientCommand() {
        return clientCommand;
    }
}
