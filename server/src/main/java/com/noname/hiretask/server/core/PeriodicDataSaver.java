package com.noname.hiretask.server.core;

import com.noname.hiretask.server.externalstorage.ExternalStorage;
import com.noname.hiretask.server.externalstorage.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Class responsible for creating a scheduled task for storing in-memory data into file storage.
 *
 * <p>To start the task use {@link PeriodicDataSaver#start()} method.
 * <p>To stop the task use {@link PeriodicDataSaver#shutdown()} method.
 */
class PeriodicDataSaver {
    private static Logger log = LoggerFactory.getLogger(PeriodicDataSaver.class);

    private final static int TASK_RATE_PERIOD_IN_SECONDS = 10;
    private final static int TASK_AWAIT_PERIOD_IN_SECONDS = 10;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private final ExternalStorage storage;

    /**
     * Creates an instance of the class with storage passed as a parameter
     *
     * @param storage storage to use when saving data
     */
    public PeriodicDataSaver(ExternalStorage storage) {
        this.storage = storage;
    }

    /**
     * Starts a periodic data saving process with a period value stored in {@link PeriodicDataSaver#TASK_RATE_PERIOD_IN_SECONDS}
     */
    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(getSaveTask(), 0, TASK_RATE_PERIOD_IN_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * Returns a {@link Runnable} which saves data to storage
     * @return {@link Runnable} which saves data to storage
     */
    public Runnable getSaveTask() {
        return this::save;
    }

    private void save() {
        final Lock writeLock = InMemoryStorage.getInstance().getWriteLock();

        //try lock is used to prevent task waiting and making a queue as it is run with fixed rate.
        if (!writeLock.tryLock()) {
            log.debug("Lock was not acquired skipping data saving.");
            return;
        }

        try {
            log.debug("Background data saving started.");
            storage.save(InMemoryStorage.getInstance().getAllBirds());
            log.debug("Background data saving finished.");
        } catch (StorageException e) {
            log.error("Error on saving data to storage.", e);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Shuts down periodic saving process
     */
    public void shutdown() {
        try {
            log.debug("Starting scheduledExecutorService shutdown.");
            scheduledExecutorService.shutdown();
            scheduledExecutorService.awaitTermination(TASK_AWAIT_PERIOD_IN_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("Error on PeriodicDataSaver shutdown.", e);
        } finally {
            log.debug("ScheduledExecutorService shutdown finished.");
        }

    }

}
