package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private final List<CelestialObject> displayedObjects = new ArrayList<>();

    public void displayObject(CelestialObject object) {
        if (isOverlapping(object)) {
            throw new IllegalArgumentException("Объект пересекается с уже существующими объектами на экране.");
        }
        displayedObjects.add(object);
    }

    public boolean isEmpty() {
        return displayedObjects.isEmpty();
    }

    public void clearDisplay() {
        displayedObjects.clear();
    }

    public List<CelestialObject> getDisplayedObjects() {
        return new ArrayList<>(displayedObjects);
    }

    private boolean isOverlapping(CelestialObject newObject) {
        for (CelestialObject existingObject : displayedObjects) {
            double distance = getDistance(existingObject.getPosition(), newObject.getPosition());
            double radiusSum = existingObject.getSize() + newObject.getSize();

            if (distance < radiusSum) {
                return true;
            }
        }
        return false;
    }

    private double getDistance(Position p1, Position p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }
}
