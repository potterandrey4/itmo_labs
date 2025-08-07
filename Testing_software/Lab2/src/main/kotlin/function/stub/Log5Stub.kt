package itmo.testing.function.stub

import itmo.testing.function.Calculable

class Log5Stub : Calculable {
    private val table = mapOf(
        1.0 to 0.0,
        5.0 to 1.0,
        25.0 to 2.0
    )

    override fun calculate(x: Double): Double = table[x] ?: Double.NaN
}
