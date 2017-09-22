package com.noname.hiretask.server.core;

import com.noname.hiretask.common.dto.SightingFilterHolder;
import com.noname.hiretask.common.model.Bird;
import com.noname.hiretask.common.model.Sighting;
import com.noname.hiretask.server.externalstorage.ExternalStorage;
import com.noname.hiretask.server.externalstorage.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 *     In memory storage for quickly managing birds.
 * <p>
 *     To sync internal data {@link ConcurrentHashMap} is used.
 *     To sync with external data saving {@link ReentrantReadWriteLock} is used.
 * <p>
 *     {@link ReentrantReadWriteLock} is used here to block collection modification while it is being saving to external storage.
 *     Writelock is available from outside of the class by getter method,
 *     and is used by {@link PeriodicDataSaver} to lock it, so collection is not modified (as modification methods use readlock) until writelock is released.
 */
public class InMemoryStorage {

    private static Logger log = LoggerFactory.getLogger(InMemoryStorage.class);

    private static final InMemoryStorage instance = new InMemoryStorage();

    private Map<String, Bird> internalMap = new ConcurrentHashMap<>();

    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = reentrantReadWriteLock.readLock();
    private Lock writeLock = reentrantReadWriteLock.writeLock();

    private InMemoryStorage() {
        if (instance != null) {
            throw new RuntimeException("Instance already created, use getInstance method.");
        }
    }

    /**
     * Method should be called to initialize internal collection with data from storage.
     *
     * @param dataStorage data storage to be used to initialize an internal collection
     */
    public void init(ExternalStorage dataStorage) {
        try {
            //write lock is used here to prevent other operation on collection before it is initialized
            getWriteLock().lock();
            internalMap = dataStorage.load().stream().collect(Collectors.toMap(Bird::getName, Function.identity()));
        } catch (StorageException e) {
            log.error("Failed to load data.", e);
        } finally {
            getWriteLock().unlock();
        }
    }

    /**
     * Getting the instance of the class
     *
     * @return {@link InMemoryStorage} instance
     */
    public static InMemoryStorage getInstance() {
        return instance;
    }

    /**
     * Adds a bird to internal collection.
     * @param bird a {@link Bird} to add
     * @throws InternalStorageException if bird name is already present in the collection
     */
    public void addBird(final Bird bird) throws InternalStorageException {
        final Lock lock = getReadLock();
        lock.lock();
        try {
            if (internalMap.putIfAbsent(bird.getName(), bird) != null) {
                throw new InternalStorageException("Bird '" + bird.getName() + "' already present in database");
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adds a {@link Sighting} to the collection os Sightings for a bird.
     * @param sighting a {@link Sighting} to add
     * @throws InternalStorageException if a bird to add a sighting for is not found
     */
    public void addSighting(final Sighting sighting) throws InternalStorageException {
        final Lock lock = getReadLock();
        lock.lock();
        try {
            final BiFunction<String, Bird, Bird> addSighting = (s, b) -> {
                b.getSightings().add(sighting);
                return b;
            };

            final Bird bird = internalMap.computeIfPresent(sighting.getBird(), addSighting);
            if (bird == null) {
                throw new InternalStorageException("Bird with name '" + sighting.getBird() + "' is not found.");
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes a bird with given name from the collection. Bird's sightings are removed as well.
     * @param birdName name of a bird to remove
     * @throws InternalStorageException if a bird with the given name is not found
     */
    public void removeBirdWithItsSightings(final String birdName) throws InternalStorageException {
        final Lock lock = getReadLock();
        lock.lock();
        try {
            final Bird bird = internalMap.remove(birdName);
            if (bird == null) {
                throw new InternalStorageException("Bird with name '" + birdName + "' is not found.");
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets all birds from the system.
     * @return List of {@link Bird}s
     */
    public List<Bird> getAllBirds() {
        return internalMap.values().stream().filter(b -> b != null).map(Function.identity())
                          .collect(Collectors.toList());
    }

    /**
     * Returns a list of {@link Sighting}s filtered by a passed filter. Birds name filtering is performed using regexp.
     * @param filterHolder holder of a filter parameters
     * @return list of filtered {@link Sighting}s
     */
    public List<Sighting> getSightingsForBird(SightingFilterHolder filterHolder) {
        final String birdName = filterHolder.getBirdName();
        final LocalDateTime startDate = filterHolder.getFrom();
        final LocalDateTime endDate = filterHolder.getTo();

        final Predicate<Sighting> isAfter = s -> s.getTime().isAfter(startDate);
        final Predicate<Sighting> isBefore = s -> s.getTime().isBefore(endDate);

        return internalMap
                .values()
                .stream()
                .filter(b -> b != null)
                .filter(b -> b.getName().matches(birdName))
                .flatMap(b -> b.getSightings().stream())
                .filter(s -> s != null)
                .filter(isAfter.and(isBefore))
                .collect(Collectors.toList());
    }

    private Lock getReadLock() {
        return readLock;
    }

    /**
     * Gets a write lock.
     *
     * @return write lock of {@link ReentrantReadWriteLock}
     */
    public Lock getWriteLock() {
        return writeLock;
    }
}
