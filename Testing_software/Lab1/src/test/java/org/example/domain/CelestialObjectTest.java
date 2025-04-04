package org.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CelestialObjectTest {

    @Test
    void starShouldHaveCorrectProperties() {
        Position position = new Position(ScreenRegion.EDGE, 10, 20);
        Star star = new Star("Красная звезда", Color.RED, 1.0, position, StarSystem.BINARY);

        assertEquals("Красная звезда", star.getName());
        assertEquals(Color.RED, star.getColor());
        assertEquals(1.0, star.getSize());
        assertEquals(position, star.getPosition());
        assertEquals(StarSystem.BINARY, star.getSystem());

        String displayText = star.getDisplayRepresentation();
        assertTrue(displayText.contains("Звезда"));
        assertTrue(displayText.contains("Красная звезда"));
        assertTrue(displayText.contains("RED"));
    }

    @Test
    void planetShouldHaveCorrectProperties() {
        Position position = new Position(ScreenRegion.CORNER, 30, 40);
        Planet planet = new Planet("Планета", Color.RED, 2.0, position, PlanetShape.CRESCENT, true);

        assertEquals("Планета", planet.getName());
        assertEquals(Color.RED, planet.getColor());
        assertEquals(2.0, planet.getSize());
        assertEquals(position, planet.getPosition());
        assertEquals(PlanetShape.CRESCENT, planet.getShape());
        assertTrue(planet.hasNightSide());

        String displayText = planet.getDisplayRepresentation();
        assertTrue(displayText.contains("Планета"));
        assertTrue(displayText.contains("RED"));
        assertTrue(displayText.contains("CRESCENT"));
        assertTrue(displayText.contains("ночная сторона"));
    }

    @Test
    void planetWithoutNightSideShouldHaveCorrectRepresentation() {
        Position position = new Position(ScreenRegion.CENTER, 50, 60);
        Planet planet = new Planet("Планета", Color.BLUE, 1.5, position, PlanetShape.CIRCLE, false);

        assertFalse(planet.hasNightSide());

        String displayText = planet.getDisplayRepresentation();
        assertTrue(displayText.contains("Планета"));
        assertTrue(displayText.contains("BLUE"));
        assertTrue(displayText.contains("CIRCLE"));
        assertFalse(displayText.contains("ночная сторона"));
    }

    @Test
    void shouldUpdatePositionCorrectly() {
        Position initialPosition = new Position(ScreenRegion.EDGE, 10, 20);
        Star star = new Star("Красная звезда", Color.RED, 1.0, initialPosition, StarSystem.SINGLE);

        Position newPosition = new Position(ScreenRegion.CENTER, 50, 60);
        star.setPosition(newPosition);

        assertEquals(newPosition, star.getPosition());
        assertNotEquals(initialPosition, star.getPosition());
    }
}
