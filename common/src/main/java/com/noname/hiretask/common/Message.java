package com.noname.hiretask.common;

/**
 * Abstract class for representing a message between a server and a client.
 * Every message should be in the same format:
 * <ul>
 * <li>At the beginning there is a header which contains a command to execute on server if the message is sent to server,
 * or command execution status if the message is sent from server.
 * <li>Header is followed by a new line symbol "\n"
 * <li>A body of the message is following after header and new line symbol
 * <li>A body of the message is followed by "\nEOL" symbols sequence which means the end of the message.
 * </ul>
 */
public abstract class Message {

    /**
     * Prepares a String message to be sent between client and server in a correct format.
     * @return String representing the full message
     */
    public String prepare() {
        return getHeader() + "\n" + getBody() + "\nEOL";
    }

    /**
     * Gets a header of the message
     * @return String with the header
     */
    abstract String getHeader();

    /**
     * Gets the body of the message
     * @return String with the body
     */
    abstract String getBody();
}
