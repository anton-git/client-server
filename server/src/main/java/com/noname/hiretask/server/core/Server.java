package com.noname.hiretask.server.core;

import com.noname.hiretask.server.externalstorage.ExternalStorage;
import com.noname.hiretask.server.externalstorage.FileStorage;
import com.noname.hiretask.server.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Main code of server logic is here.
 */
public class Server {

    private static Logger log = LoggerFactory.getLogger(Server.class);
    private final static long SECONDS_TO_WAIT_POOL_SHUTDOWN = 10;

    private Settings settings;

    private ExecutorService pool;
    private ServerSocket serverSocket;
    private PeriodicDataSaver dataSaver;
    private ExternalStorage dataStorage;

    public Server(Settings settings) {
        this.settings = settings;
        this.dataStorage = new FileStorage(settings.getDataFolder());
    }

    /**
     * Starts the server which includes:
     * <ul>
     * <li> thread pool initialization
     * <li> preloading data from external storage
     * <li> starting a periodic thread which saves in-memory data to external storage
     * </ul>
     */
    public void start() {
        log.info("Starting server.");

        pool = Executors.newFixedThreadPool(settings.getProcCount());
        preloadData();
        startStorageSaver();

        try {
            startServerSocketWithHandler(ClientRequestHandler.getClientHandler(quitProcess()));
        } catch (SocketException e) {
            //this can be caught if server socket was closed explicitly
            log.debug(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
        } finally {
            shutDownPoolOfThreads(SECONDS_TO_WAIT_POOL_SHUTDOWN);
            shutDownStorageSaver();
        }

        log.info("Server stopped.");
    }

    private void startStorageSaver() {
        dataSaver = new PeriodicDataSaver(dataStorage);
        dataSaver.start();

        Runtime.getRuntime().addShutdownHook(new Thread(dataSaver.getSaveTask()));
    }

    private void shutDownStorageSaver() {
        dataSaver.shutdown();
    }

    private void preloadData() {
        InMemoryStorage.getInstance().init(dataStorage);
    }

    private void startServerSocketWithHandler(Function<Socket, Runnable> handler) throws IOException {
        serverSocket = new ServerSocket(settings.getPort());
        log.info("Server started.");

        int counter = 0;
        //exit when server socket is closed and its accept method throws exception
        while (true) {
            //socket is closed in handler
            final Socket socket = serverSocket.accept();
            log.info("Client #{} has connected.", counter++);
            pool.submit(handler.apply(socket));
            log.debug("Task submitted for execution");
        }
    }

    private Consumer<Boolean> quitProcess() {
        // the reason of calling close explicitly is to break serverSocket accept method, which is waiting for connection.
        //Alternatively new client connection from here can be made
        return b -> {
            log.debug("Got the QUIT command, closing server socket.");
            try {
                serverSocket.close();
                log.debug("Server socket is closed.");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        };
    }

    private void shutDownPoolOfThreads(long timeoutInSeconds) {
        try {
            log.debug("Starting thread pool shutdown.");
            pool.shutdown();
            pool.awaitTermination(timeoutInSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.warn("Exception on await termination of the pool.", e);
        } finally {
            pool.shutdownNow();
            log.debug("Thread pool shutdown finished.");
        }
    }

}
