package com.noname.hiretask.client.command;

import com.noname.hiretask.client.ClientCommand;
import com.noname.hiretask.client.command.executor.CommandExecutor;
import com.noname.hiretask.client.command.executor.impl.*;
import com.noname.hiretask.client.settings.GlobalSettings;
import com.noname.hiretask.client.util.ResponseMessageExtractor;
import com.noname.hiretask.client.util.UserConsoleUtil;
import com.noname.hiretask.common.RequestMessage;
import com.noname.hiretask.common.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class for communicating with server.
 * It is responsible for sending message to server and receiving it.
 */
public class ServerCommunicator {

    private static Logger log = LoggerFactory.getLogger(ServerCommunicator.class);

    private final int port;

    public ServerCommunicator(int port) {
        this.port = port;
    }

    /**
     * <p>
     * Makes a communication with a server based on a passed command.
     * <p>
     * Basically method prepare a message for the server, using user input if required any information from user.
     * Then it send message to the server, waits for response and prints the result for the user.
     *
     * @param clientCommand client command of type {@link ClientCommand}
     */
    public void communicate(ClientCommand clientCommand) {
        final CommandExecutor executor = getCommandExecutor(clientCommand);
        final RequestMessage requestMessage = executor.prepareMessageForServer();

        try (final Socket socket = new Socket(GlobalSettings.DEFAULT_HOST, port);
             final PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

            socket.setSoTimeout(GlobalSettings.SOCKET_TIMEOUT_IN_MILLISECONDS);

            pw.println(requestMessage.prepare());
            UserConsoleUtil.println("Data sent to server.");

            final ResponseMessage response = ResponseMessageExtractor.extract(br);
            UserConsoleUtil.println("Got a response.");
            executor.acceptServerResponse(response);

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            UserConsoleUtil.println("Error happened while communicating with server : " + e.getMessage());
            log.error("Error while communicating with server.", e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private static CommandExecutor getCommandExecutor(ClientCommand clientCommand) {
        switch (clientCommand) {
            case ADD_BIRD: {
                return new AddBirdExecutor();
            }
            case ADD_SIGHTING: {
                return new AddSightingExecutor();
            }
            case LIST_BIRDS: {
                return new ListAllBirdsExecutor();
            }
            case LIST_SIGHTINGS: {
                return new ListSightingsExecutor();
            }
            case REMOVE: {
                return new RemoveExecutor();
            }
            case QUIT: {
                return new QuitExecutor();
            }
            default:
                throw new IllegalArgumentException("Unknown command: " + clientCommand);
        }
    }

}
