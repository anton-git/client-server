package com.noname.hiretask.server.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Validator of app settings which were passed into the app by a user.
 */
public class SettingsValidator {

    private final static Logger log = LoggerFactory.getLogger(SettingsValidator.class);

    private Settings settings;

    public SettingsValidator(Settings settings) {
        this.settings = settings;
    }

    /**
     * Validates directory where app data is stored.
     * Check directory have read/write access.
     * If the directory does not exist then creates it.
     *
     * @throws DirectoryAccessException
     */
    public void validateDirectory() throws DirectoryAccessException {
        log.debug("Validating directory: {}", settings.getDataFolder());

        final File dir = new File(settings.getDataFolder());
        if (dir.exists()) {
            log.debug("Directory exists.");
            validateDirectoryAccess(dir);
        } else {
            createDirectory(dir);
        }
    }

    private void createDirectory(final File dir) throws DirectoryAccessException {
        log.info("Creating the directory '{}' as it does not exist.", dir.getAbsolutePath());

        if (dir.mkdirs()) {
            log.info("Directory {} was created.", settings.getDataFolder());
        } else {
            throw new DirectoryAccessException("Unable to create the directory: " + settings.getDataFolder());
        }
    }

    private void validateDirectoryAccess(final File dir) throws DirectoryAccessException {
        log.debug("Checking directory read access.");
        if (!dir.canRead()) {
            throw new DirectoryAccessException("Unable to read from the directory: " + settings.getDataFolder());
        }

        log.debug("Checking directory write access.");
        if (!dir.canWrite()) {
            throw new DirectoryAccessException("Unable to write to the directory: " + settings.getDataFolder());
        }

        tryCreateFile(dir);
    }

    private void tryCreateFile(final File dir) throws DirectoryAccessException {
        final String testFileName = "testaccess.txt";
        final File testFile = new File(dir, testFileName);
        try {
            final boolean created = testFile.createNewFile();
            log.debug("Test file was created: {}", created);
            if (!(testFile.canRead() && testFile.canWrite())) {
                throw new DirectoryAccessException(
                        "Read/Write access to files is restricted in folder: " + settings.getDataFolder());
            }
        } catch (IOException e) {
            log.debug("Error on test file writing.", e);
            throw new DirectoryAccessException("Unable to create files in the directory: " + settings.getDataFolder());
        } finally {
            testFile.delete();
        }
    }
}
