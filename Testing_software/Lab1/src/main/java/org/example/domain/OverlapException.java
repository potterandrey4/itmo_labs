package org.example.domain;

public class OverlapException extends IllegalArgumentException {
    private final CelestialObject existingObject;
    private final CelestialObject newObject;

    public OverlapException(CelestialObject existingObject, CelestialObject newObject) {
        this.existingObject = existingObject;
        this.newObject = newObject;
    }

    public CelestialObject getExistingObject() {
        return existingObject;
    }

    public CelestialObject getNewObject() {
        return newObject;
    }
}
