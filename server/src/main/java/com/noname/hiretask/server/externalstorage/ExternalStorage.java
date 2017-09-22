package com.noname.hiretask.server.externalstorage;

import com.noname.hiretask.common.model.Bird;

import java.util.List;

/**
 * Interface for an external storage of data.
 */
public interface ExternalStorage {

    /**
     * Saves passed data to a storage.
     *
     * @param birds passed list of {@link Bird}s to save
     * @throws StorageException
     */
    void save(final List<Bird> birds) throws StorageException;

    /**
     * Retrieves data from a storage.
     *
     * @return List of {@link Bird}s retrieved from a storage
     * @throws StorageException
     */
    List<Bird> load() throws StorageException;

}
