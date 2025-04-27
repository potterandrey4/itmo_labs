package org.example.domain;

import lombok.Getter;

public class Planet extends CelestialObject {
    private final PlanetShape shape;
    private final boolean hasNightSide;

    public Planet(Name name, Color color, double size, Position position,
            PlanetShape shape, boolean hasNightSide) {
        super(name, color, size, position);
        this.shape = shape;
        this.hasNightSide = hasNightSide;
    }

    public boolean hasNightSide() {
        return hasNightSide;
    }

    @Override
    protected boolean isStarObject() {
        return false;
    }

    public PlanetShape getShape() {
        return shape;
    }
}
