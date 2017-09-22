package com.noname.hiretask.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a bird - domain object.
 * It has 4 properties: name, color, weight and height.
 * Also a bird contains a collection of sightings.
 *
 * @see Sighting
 */
public class Bird {
    private String name;
    private String color;
    private int weight;
    private int height;
    private List<Sighting> sightings = new ArrayList<>();

    public Bird() {
    }

    public Bird(String name, String color, int weight, int height) {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
    }

    public Bird(String name, String color, int weight, int height,
                List<Sighting> sightings) {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
        this.sightings = sightings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }
}
