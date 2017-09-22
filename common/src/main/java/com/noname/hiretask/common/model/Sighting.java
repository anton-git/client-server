package com.noname.hiretask.common.model;

import java.time.LocalDateTime;

/**
 * Class represents a sighting.
 * It has location and date time.
 * Each sighting belongs to a bird.
 *
 * @see Bird
 */
public class Sighting {

    private String location;
    private LocalDateTime time;
    private String bird;

    public Sighting() {
    }

    public Sighting(String location, LocalDateTime time) {
        this(location, time, null);
    }

    public Sighting(String location, LocalDateTime time, String bird) {
        this.location = location;
        this.time = time;
        this.bird = bird;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getBird() {
        return bird;
    }

    public void setBird(String bird) {
        this.bird = bird;
    }
}
