package org.example;

import org.example.domain.*;

import java.util.ArrayList;
import java.util.List;

public class SpaceSimulation {
    private final Screen screen;
    private final List<CelestialObject> celestialObjects;
    private SimulationState currentState;

    public SpaceSimulation() {
        this.screen = new Screen();
        this.celestialObjects = new ArrayList<>();
        this.currentState = SimulationState.NOT_STARTED;
    }

    public void start() {
        this.currentState = SimulationState.STARTED;
        screen.clearDisplay();
    }

    public void addStar(Star star) {
        celestialObjects.add(star);
        if (currentState == SimulationState.STARTED) {
            screen.displayObject(star);
        }
    }

    public void addPlanet(Planet planet) {
        celestialObjects.add(planet);
        if (currentState == SimulationState.STARTED) {
            screen.displayObject(planet);
        }
    }

    public SimulationState getCurrentState() {
        return currentState;
    }

    public List<CelestialObject> getCelestialObjects() {
        return new ArrayList<>(celestialObjects);
    }

    public Screen getScreen() {
        return screen;
    }
}
