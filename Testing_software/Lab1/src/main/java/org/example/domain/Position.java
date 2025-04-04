package org.example.domain;

import lombok.Getter;
import lombok.Setter;

public class Position {
    private ScreenRegion region;
    private double x;
    private double y;

    public Position(ScreenRegion region, double x, double y) {
        this.region = region;
        this.x = x;
        this.y = y;
    }

    public ScreenRegion getRegion() {
        return region;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}