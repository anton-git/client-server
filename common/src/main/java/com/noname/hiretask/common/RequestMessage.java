package com.noname.hiretask.common;

/**
 * Request message is sent from a client to a server.
 */
public class RequestMessage extends Message {

    private final ProtocolCommand command;
    private final String body;

    public RequestMessage(ProtocolCommand command, String body) {
        this.command = command;
        this.body = body;
    }

    @Override
    String getHeader() {
        return command.name();
    }

    @Override
    public String getBody() {
        return body;
    }
}
