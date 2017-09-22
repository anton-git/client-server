package com.noname.hiretask.server.externalstorage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.noname.hiretask.common.model.Bird;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ExternalStorage} interface. File storage is used.
 * Data is stored in "birds.json" file that placed in a folder passed in the constructor.
 */
public class FileStorage implements ExternalStorage {

    private static Logger log = LoggerFactory.getLogger(FileStorage.class);
    private static final TypeReference<List<Bird>> LIST_OF_BIRDS_TYPE_REF = new TypeReference<List<Bird>>() {};

    private static final String storageFileName = "birds.json";
    private static final String fileSeparator = System.getProperty("file.separator");
    private static final String defaultFolder = System.getProperty("user.home") + fileSeparator + "serverdata";

    private String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public FileStorage() {
        this(defaultFolder);
    }

    /**
     * Constructs an instance of the class with storage file placed in the folder passed as argument.
     *
     * @param customFolder folder to store file with data
     */
    public FileStorage(String customFolder) {
        this.fileName = customFolder + fileSeparator + storageFileName;
    }

    @Override
    public void save(List<Bird> birds) throws StorageException {

        final File newFile = new File(fileName);

        try {
            mapper.writeValue(newFile, birds);
        } catch (IOException e) {
            log.error("Failed to save data to storage.", e.getMessage());
            throw new StorageException("Failed to save data to storage.", e);
        }
    }

    @Override
    public List<Bird> load() throws StorageException {
        final File file = new File(fileName);
        if (!file.exists()) {
            log.info("Storage file {} does not exist, no data to initialize.", fileName);
            return new ArrayList<>();
        }
        try {
            log.info("Loading data from external storage to in-memory.");
            return mapper.readValue(file, LIST_OF_BIRDS_TYPE_REF);
        } catch (IOException e) {
            log.error("Failed to load data from storage", e.getMessage());
            throw new StorageException("Failed to read from storage.", e);
        }
    }

}
