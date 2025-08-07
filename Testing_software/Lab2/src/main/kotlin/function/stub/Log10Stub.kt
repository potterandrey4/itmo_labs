package itmo.testing.function.stub

import itmo.testing.function.Calculable

class Log10Stub : Calculable {
    private val table = mapOf(
        1.0 to 0.0,
        10.0 to 1.0,
        100.0 to 2.0
    )

    override fun calculate(x: Double): Double = table[x] ?: Double.NaN
}