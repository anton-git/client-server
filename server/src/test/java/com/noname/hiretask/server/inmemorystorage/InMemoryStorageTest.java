package com.noname.hiretask.server.inmemorystorage;

import com.noname.hiretask.common.dto.SightingFilterHolder;
import com.noname.hiretask.server.core.InMemoryStorage;
import com.noname.hiretask.server.core.InternalStorageException;
import com.noname.hiretask.server.externalstorage.ExternalStorage;
import com.noname.hiretask.server.externalstorage.StorageException;
import com.noname.hiretask.common.model.Bird;
import com.noname.hiretask.common.model.Sighting;
import org.junit.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for in-memory storage
 */
public class InMemoryStorageTest {

    private final static InMemoryStorage storage = InMemoryStorage.getInstance();


    @Before
    public void runBeforeClass() {
        storage.init(new ExternalStorage() {
            @Override
            public void save(List<Bird> birds) throws StorageException {
            }

            @Override
            public List<Bird> load() throws StorageException {
                return new ArrayList<>();
            }
        });
    }

    @Test(expected = Test.None.class)
    public void addBird() throws Exception {
        final List<Bird> initialBirds = storage.getAllBirds();
        Assert.assertTrue(initialBirds.isEmpty());

        storage.addBird(new Bird("A", "RED", 12, 12));
    }

    @Test(expected = Test.None.class)
    public void addBirdAndGetAll() throws Exception {
        final List<Bird> initialBirds = storage.getAllBirds();
        Assert.assertTrue(initialBirds.isEmpty());

        storage.addBird(new Bird("A", "RED", 12, 12));
        Assert.assertEquals(1, storage.getAllBirds().size());
    }

    @Test(expected = InternalStorageException.class)
    public void addBirdWithSameName() throws Exception {
        final List<Bird> initialBirds = storage.getAllBirds();
        Assert.assertTrue(initialBirds.isEmpty());

        storage.addBird(new Bird("A", "RED", 12, 12));
        storage.addBird(new Bird("A", "BLUE", 13, 13));
    }

    @Test(expected = Test.None.class)
    public void addSightingToABird() throws Exception {
        final List<Bird> initialBirds = storage.getAllBirds();
        Assert.assertTrue(initialBirds.isEmpty());

        storage.addBird(new Bird("A", "RED", 12, 12));
        storage.addSighting(new Sighting("Madrid", LocalDateTime.of(2017, 9, 9, 9, 9, 9, 9), "A"));
    }

    @Test(expected = InternalStorageException.class)
    public void addSightingToUnknownBird() throws Exception {
        final List<Bird> initialBirds = storage.getAllBirds();
        Assert.assertTrue(initialBirds.isEmpty());

        storage.addBird(new Bird("A", "RED", 12, 12));
        storage.addSighting(new Sighting("Madrid", LocalDateTime.of(2017, 9, 9, 9, 9, 9, 9), "Z"));
    }

    @Test(expected = Test.None.class)
    public void removeBird() throws Exception {
        final List<Bird> initialBirds = storage.getAllBirds();
        Assert.assertTrue(initialBirds.isEmpty());

        storage.addBird(new Bird("A", "RED", 12, 12));
        storage.removeBirdWithItsSightings("A");
    }

    @Test(expected = InternalStorageException.class)
    public void removeUnknownBird() throws Exception {
        final List<Bird> initialBirds = storage.getAllBirds();
        Assert.assertTrue(initialBirds.isEmpty());

        storage.addBird(new Bird("A", "RED", 12, 12));
        storage.removeBirdWithItsSightings("W");
    }

    @Test
    public void getSightings() throws Exception {
        final List<Bird> initialBirds = storage.getAllBirds();
        Assert.assertTrue(initialBirds.isEmpty());

        Assert.assertTrue(getFilteredSightings("A").isEmpty());

        storage.addBird(new Bird("A", "#AAA", 10, 100));
        Assert.assertTrue(getFilteredSightings("A").isEmpty());

        storage.addSighting(new Sighting("LOCATION-1", LocalDateTime.now(), "A"));
        Assert.assertEquals(1, getFilteredSightings("A").size());

        storage.addBird(new Bird("AA", "#ABC", 10, 100));
        Assert.assertEquals(1, getFilteredSightings("A").size());

        storage.addSighting(new Sighting("LOCATION-2", LocalDateTime.now(), "A"));
        Assert.assertEquals(2, getFilteredSightings("A").size());


        storage.addSighting(new Sighting("LOCATION-3", LocalDateTime.now(), "AA"));
        Assert.assertEquals(3, getFilteredSightings(".*").size());
    }

    private List<Sighting> getFilteredSightings(final String birdName) {
        return storage.getSightingsForBird(SightingFilterHolder.of(birdName,
                                                                   LocalDateTime.now().minusDays(1),
                                                                   LocalDateTime.now().plusDays(1)));
    }
}