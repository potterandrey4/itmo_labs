package org.example.domain;

import org.example.SpaceSimulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceSimulationTest {

    private SpaceSimulation simulation;

    @BeforeEach
    void setUp() {
        simulation = new SpaceSimulation();
    }

    @Test
    void initialStateIsNotStarted() {
        assertEquals(SimulationState.NOT_STARTED, simulation.getCurrentState());
        assertTrue(simulation.getScreen().isEmpty());
        assertTrue(simulation.getCelestialObjects().isEmpty());
    }

    @Test
    void startChangesStateToStarted() {
        simulation.start();
        assertEquals(SimulationState.STARTED, simulation.getCurrentState());
    }

    @Test
    void addStarDisplaysObjectWhenSimulationStarted() {
        simulation.start();

        Star star = new Star("Красная звезда", Color.RED, 1.0,
                new Position(ScreenRegion.EDGE, 10, 20), StarSystem.SINGLE);
        simulation.addStar(star);

        // Проверяем, что звезда добавлена в список объектов
        assertEquals(1, simulation.getCelestialObjects().size());
        assertTrue(simulation.getCelestialObjects().contains(star));

        // Проверяем, что звезда отображается на экране
        assertFalse(simulation.getScreen().isEmpty());
        assertEquals(1, simulation.getScreen().getDisplayedObjects().size());
        assertTrue(simulation.getScreen().getDisplayedObjects().contains(star));
    }

    @Test
    void addStarDoesNotDisplayObjectWhenSimulationNotStarted() {
        Star star = new Star("Красная звезда", Color.RED, 1.0,
                new Position(ScreenRegion.EDGE, 10, 20), StarSystem.SINGLE);
        simulation.addStar(star);

        // Проверяем, что звезда добавлена в список объектов
        assertEquals(1, simulation.getCelestialObjects().size());
        assertTrue(simulation.getCelestialObjects().contains(star));

        // Проверяем, что звезда НЕ отображается на экране
        assertTrue(simulation.getScreen().isEmpty());
        assertEquals(0, simulation.getScreen().getDisplayedObjects().size());
    }

    @Test
    void addPlanetDisplaysObjectWhenSimulationStarted() {
        simulation.start();

        Planet planet = new Planet("Планета", Color.RED, 2.0,
                new Position(ScreenRegion.CORNER, 30, 40),
                PlanetShape.CRESCENT, true);
        simulation.addPlanet(planet);

        // Проверяем, что планета добавлена в список объектов
        assertEquals(1, simulation.getCelestialObjects().size());
        assertTrue(simulation.getCelestialObjects().contains(planet));

        // Проверяем, что планета отображается на экране
        assertFalse(simulation.getScreen().isEmpty());
        assertEquals(1, simulation.getScreen().getDisplayedObjects().size());
        assertTrue(simulation.getScreen().getDisplayedObjects().contains(planet));
    }

    @Test
    void addPlanetDoesNotDisplayObjectWhenSimulationNotStarted() {
        Planet planet = new Planet("Планета", Color.RED, 2.0,
                new Position(ScreenRegion.CORNER, 30, 40),
                PlanetShape.CRESCENT, true);
        simulation.addPlanet(planet);

        // Проверяем, что планета добавлена в список объектов
        assertEquals(1, simulation.getCelestialObjects().size());
        assertTrue(simulation.getCelestialObjects().contains(planet));

        // Проверяем, что планета НЕ отображается на экране
        assertTrue(simulation.getScreen().isEmpty());
        assertEquals(0, simulation.getScreen().getDisplayedObjects().size());
    }

    @Test
    void addMultipleObjectsToSimulation() {
        simulation.start();

        Star star1 = new Star("Красная звезда", Color.RED, 1.0,
                new Position(ScreenRegion.EDGE, 10, 20), StarSystem.BINARY);
        Star star2 = new Star("Вторая звезда", Color.RED, 0.8,
                new Position(ScreenRegion.EDGE, 15, 25), StarSystem.BINARY);
        Planet planet = new Planet("Планета", Color.RED, 2.0,
                new Position(ScreenRegion.CORNER, 30, 40),
                PlanetShape.CRESCENT, true);

        simulation.addStar(star1);
        simulation.addStar(star2);
        simulation.addPlanet(planet);

        // Проверяем, что все объекты добавлены
        assertEquals(3, simulation.getCelestialObjects().size());
        assertTrue(simulation.getCelestialObjects().contains(star1));
        assertTrue(simulation.getCelestialObjects().contains(star2));
        assertTrue(simulation.getCelestialObjects().contains(planet));

        // Проверяем, что все объекты отображаются
        assertFalse(simulation.getScreen().isEmpty());
        assertEquals(3, simulation.getScreen().getDisplayedObjects().size());
        assertTrue(simulation.getScreen().getDisplayedObjects().contains(star1));
        assertTrue(simulation.getScreen().getDisplayedObjects().contains(star2));
        assertTrue(simulation.getScreen().getDisplayedObjects().contains(planet));
    }
}
