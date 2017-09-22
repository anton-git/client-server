package com.noname.hiretask.common.dto;

import java.time.LocalDateTime;

/**
 * A holder for parameters for filtering sightings.
 */
public class SightingFilterHolder {

    private String birdName;
    private LocalDateTime from;
    private LocalDateTime to;

    public SightingFilterHolder() {
    }

    public static SightingFilterHolder of(String birdName, LocalDateTime from, LocalDateTime to) {
        final SightingFilterHolder filterHolder = new SightingFilterHolder();
        filterHolder.setBirdName(birdName);
        filterHolder.setFrom(from);
        filterHolder.setTo(to);
        return filterHolder;
    }

    public void setBirdName(String birdName) {
        this.birdName = birdName;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public String getBirdName() {
        return birdName;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
