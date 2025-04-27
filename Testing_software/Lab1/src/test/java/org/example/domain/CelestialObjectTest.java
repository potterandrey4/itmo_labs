package org.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CelestialObjectTest {

    @Test
    void starShouldHaveCorrectProperties() {
        Position position = new Position(ScreenRegion.EDGE, 10, 20);
        Star star = new Star(Name.RED_STAR, Color.RED, 1.0, position, StarSystem.BINARY);

        assertEquals(Name.RED_STAR, star.getName());
        assertEquals(Color.RED, star.getColor());
        assertEquals(1.0, star.getSize());
        assertEquals(position, star.getPosition());
        assertEquals(StarSystem.BINARY, star.getSystem());
        assertEquals(ObjectType.STAR, star.getObjectType());
    }

    @Test
    void planetShouldHaveCorrectProperties() {
        Position position = new Position(ScreenRegion.CORNER, 30, 40);
        Planet planet = new Planet(Name.PLANET, Color.RED, 2.0, position, PlanetShape.CRESCENT, true);

        assertEquals(Name.PLANET, planet.getName());
        assertEquals(Color.RED, planet.getColor());
        assertEquals(2.0, planet.getSize());
        assertEquals(position, planet.getPosition());
        assertEquals(PlanetShape.CRESCENT, planet.getShape());
        assertTrue(planet.hasNightSide());
        assertEquals(ObjectType.PLANET, planet.getObjectType());
    }

    @Test
    void planetWithoutNightSideShouldHaveCorrectProperties() {
        Position position = new Position(ScreenRegion.CENTER, 50, 60);
        Planet planet = new Planet(Name.BLUE_PLANET, Color.BLUE, 1.5, position, PlanetShape.CIRCLE, false);

        assertFalse(planet.hasNightSide());
        assertEquals(PlanetShape.CIRCLE, planet.getShape());
        assertEquals(Name.BLUE_PLANET, planet.getName());
        assertEquals(Color.BLUE, planet.getColor());
    }

    @Test
    void shouldUpdatePositionCorrectly() {
        Position initialPosition = new Position(ScreenRegion.EDGE, 10, 20);
        Star star = new Star(Name.YELLOW_STAR, Color.RED, 1.0, initialPosition, StarSystem.SINGLE);

        Position newPosition = new Position(ScreenRegion.CENTER, 50, 60);
        star.setPosition(newPosition);

        assertEquals(newPosition, star.getPosition());
        assertNotEquals(initialPosition, star.getPosition());
    }
}
