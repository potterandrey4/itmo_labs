package org.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void positionShouldStoreCorrectValues() {
        Position position = new Position(ScreenRegion.EDGE, 10.5, 20.3);

        assertEquals(ScreenRegion.EDGE, position.getRegion());
        assertEquals(10.5, position.getX());
        assertEquals(20.3, position.getY());
    }

    @Test
    void differentPositionsShouldNotBeEqual() {
        Position position1 = new Position(ScreenRegion.EDGE, 10, 20);
        Position position2 = new Position(ScreenRegion.EDGE, 10, 30);
        Position position3 = new Position(ScreenRegion.CORNER, 10, 20);

        assertNotEquals(position1, position2);
        assertNotEquals(position1, position3);
        assertNotEquals(position2, position3);
    }
}
