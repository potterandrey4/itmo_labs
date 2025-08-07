package itmo.testing.function.stub

import itmo.testing.function.Calculable

class Log3Stub : Calculable {
    private val table = mapOf(
        1.0 to 0.0,
        3.0 to 1.0,
        9.0 to 2.0
    )

    override fun calculate(x: Double): Double = table[x] ?: Double.NaN
}
