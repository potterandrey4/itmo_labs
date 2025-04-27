package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private final List<CelestialObject> displayedObjects = new ArrayList<>();

    public boolean isEmpty() {
        return displayedObjects.isEmpty();
    }

    public void displayObject(CelestialObject object) {
        // Проверяем, не пересекается ли новый объект с уже существующими
        for (CelestialObject existingObject : displayedObjects) {
            if (objectsOverlap(existingObject, object)) {
                throw new OverlapException(existingObject, object);
            }
        }

        displayedObjects.add(object);
    }

    private boolean objectsOverlap(CelestialObject obj1, CelestialObject obj2) {
        // Получаем позиции и размеры объектов
        Position pos1 = obj1.getPosition();
        Position pos2 = obj2.getPosition();
        double size1 = obj1.getSize();
        double size2 = obj2.getSize();

        // Вычисляем расстояние между центрами объектов
        double distance = Math.sqrt(
                Math.pow(pos1.getX() - pos2.getX(), 2) +
                        Math.pow(pos1.getY() - pos2.getY(), 2));

        // Если расстояние меньше суммы радиусов, то объекты пересекаются
        return distance < (size1 + size2);
    }

    public void clearDisplay() {
        displayedObjects.clear();
    }

    public List<CelestialObject> getDisplayedObjects() {
        return new ArrayList<>(displayedObjects);
    }
}
