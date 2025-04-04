package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ArcCosCalculatorTest {

    private static final double DELTA = 1e-4;
    private ArcCosCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new ArcCosCalculator();
    }

    @ParameterizedTest(name = "arccos({0}) должен быть примерно {1}")
    @CsvSource({
            "1.0, 0.0",
            "0.866025, 0.5235987",  // arccos(√3/2) = π/6
            "0.0, 1.5707963",       // arccos(0) = π/2
            "-0.866025, 2.6179938", // arccos(-√3/2) = 5π/6
            "-1.0, 3.1415926"       // arccos(-1) = π
    })
    void shouldCalculateArcCosCorrectly(double x, double expected) {
        assertEquals(expected, calculator.calculate(x), DELTA);
    }

    @ParameterizedTest(name = "Расчет для значения {0}, близкого к границе интервала")
    @ValueSource(doubles = { 1.0, -1.0, 0.9999, -0.9999, 0.0001, -0.0001 })
    void shouldCalculateValuesCloseToSpecialCases(double x) {
        double expected = Math.acos(x);
        assertEquals(expected, calculator.calculate(x), DELTA);
    }

    @ParameterizedTest(name = "Расчет для значения {0} вне интервала [-1, 1]")
    @ValueSource(doubles = { 1.1, -1.1 })
    void shouldThrowExceptionForValuesOutsideOfRange(double x) {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(x));
    }
}
