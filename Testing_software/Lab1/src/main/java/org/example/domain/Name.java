package org.example.domain;

public enum Name {
    RED_STAR("Красная звезда"),
    PLANET("Планета"),
    BLUE_PLANET("Синяя планета"),
    YELLOW_STAR("Желтая звезда"),
    STAR("Звезда");

    private final char[] characters;

    Name(String nameValue) {
        this.characters = nameValue.toCharArray();
    }
}
