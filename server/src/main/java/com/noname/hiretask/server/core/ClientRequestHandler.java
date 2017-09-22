package com.noname.hiretask.server.core;

import com.noname.hiretask.common.Message;
import com.noname.hiretask.common.ProtocolCommand;
import com.noname.hiretask.common.ResponseMessage;
import com.noname.hiretask.server.executor.Executor;
import com.noname.hiretask.server.executor.ExecutorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class is responsible for handling client requests.
 */
class ClientRequestHandler {

    private static Logger log = LoggerFactory.getLogger(ClientRequestHandler.class);

    /**
     * Returns a {@link Function} that accepts {@link Socket} and produces {@link Runnable} which handles requests to the server.
     *
     * @param onQuitCommandConsumer action that should be performed when a command to shut down the server is received.
     * @return {@link Function} that accepts {@link Socket} and produces {@link Runnable}
     */
    static Function<Socket, Runnable> getClientHandler(Consumer<Boolean> onQuitCommandConsumer) {
        return socket -> (() -> handleClient(socket, onQuitCommandConsumer));
    }

    private static void handleClient(final Socket socketSource, Consumer<Boolean> onQuitCommandConsumer) {
        // socketSource parameter MUST be closed here so used in try-with-resources
        try (
                final Socket socket = socketSource;
                final Writer writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                final PrintWriter pw = new PrintWriter(writer, true);
                final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))
        ) {

            try {
                final ProtocolCommand protocolCommand = extractProtocolCommand(br);
                if (ProtocolCommand.QUIT.equals(protocolCommand)) {
                    onQuitCommandConsumer.accept(true);
                }
                final Executor executor = ExecutorFactory.getExecutor(protocolCommand);

                final StringBuilder stringBuilder = extractBody(br);
                final Message responseMessage = executor.execute(stringBuilder.toString());

                pw.println(responseMessage.prepare());
                log.debug("Response was sent.");

            } catch (Exception e) {
                log.error("Error on request handling happened.", e);
                sendErrorMessageToClient(pw, e);
            }

            log.debug("Finished serving a client.");
        } catch (IOException e) {
            log.error("Error on client handling", e);
        }
    }

    private static void sendErrorMessageToClient(final PrintWriter pw, final Exception e) {
        final Message responseMessage = new ResponseMessage(ResponseMessage.ResponseCode.FAILED, e.getMessage());
        pw.println(responseMessage.prepare());
        log.info("Response with error message was sent.");
    }

    private static ProtocolCommand extractProtocolCommand(final BufferedReader br) throws IOException {
        final String command = br.readLine();
        log.debug("Command: {}", command);
        return getCommand(command);
    }

    private static ProtocolCommand getCommand(final String commandAsString) {
        if (commandAsString == null || commandAsString.isEmpty()) {
            throw new IllegalArgumentException("Command can not be empty");
        }

        return ProtocolCommand.valueOf(commandAsString);
    }

    private static StringBuilder extractBody(BufferedReader br) throws IOException {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        while ((line = br.readLine()) != null) {
            log.debug("{} - {}", counter++, line);

            if ("EOL".equals(line)) {
                break;
            }

            stringBuilder.append(line);
        }

        log.debug("Body: {}", stringBuilder.toString());
        return stringBuilder;
    }
}
