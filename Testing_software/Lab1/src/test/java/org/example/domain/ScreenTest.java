package org.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScreenTest {

    private Screen screen;
    private Star star;
    private Planet planet;

    @BeforeEach
    void setUp() {
        screen = new Screen();
        star = new Star("Звезда", Color.RED, 1.0,
                new Position(ScreenRegion.EDGE, 10, 20), StarSystem.SINGLE);
        planet = new Planet("Планета", Color.BLUE, 2.0,
                new Position(ScreenRegion.CORNER, 30, 40),
                PlanetShape.CRESCENT, true);
    }

    @Test
    void screenShouldBeEmptyInitially() {
        assertTrue(screen.isEmpty());
        assertEquals(0, screen.getDisplayedObjects().size());
    }

    @Test
    void displayObjectShouldAddObjectToScreen() {
        screen.displayObject(star);

        assertFalse(screen.isEmpty());
        assertEquals(1, screen.getDisplayedObjects().size());
        assertTrue(screen.getDisplayedObjects().contains(star));
    }

    @Test
    void displayMultipleObjectsShouldAddAllObjects() {
        screen.displayObject(star);
        screen.displayObject(planet);

        assertFalse(screen.isEmpty());
        assertEquals(2, screen.getDisplayedObjects().size());
        assertTrue(screen.getDisplayedObjects().contains(star));
        assertTrue(screen.getDisplayedObjects().contains(planet));
    }

    @Test
    void clearDisplayShouldRemoveAllObjects() {
        screen.displayObject(star);
        screen.displayObject(planet);
        assertFalse(screen.isEmpty());

        // Затем очищаем экран
        screen.clearDisplay();

        // Проверяем, что экран пуст
        assertTrue(screen.isEmpty());
        assertEquals(0, screen.getDisplayedObjects().size());
    }

    @Test
    void getDisplayedObjectsShouldReturnCopy() {
        screen.displayObject(star);

        // Получаем список и модифицируем его
        var displayedObjects = screen.getDisplayedObjects();
        displayedObjects.add(planet);

        // Проверяем, что оригинальный список в экране не изменился
        assertEquals(1, screen.getDisplayedObjects().size());
        assertFalse(screen.getDisplayedObjects().contains(planet));
    }

    @Test
    void displayObjectShouldThrowExceptionIfOverlapping() {
        Screen screen = new Screen();
        CelestialObject star = new Star("Звезда", Color.RED, 5.0, new Position(ScreenRegion.CENTER, 50, 50), StarSystem.SINGLE);
        CelestialObject planet = new Planet("Планета", Color.BLUE, 3.0, new Position(ScreenRegion.CENTER, 51, 51), PlanetShape.CRESCENT, true);

        screen.displayObject(star);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> screen.displayObject(planet));
        assertEquals("Объект пересекается с уже существующими объектами на экране.", exception.getMessage());
    }
}
