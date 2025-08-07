package itmo.testing.function.stub

import itmo.testing.function.Calculable

class LnStub : Calculable {
    private val table = mapOf(
        1.0 to 0.0,
        2.7183 to 1.0,
        10.0 to 2.3026
    )

    override fun calculate(x: Double): Double = table[x] ?: Double.NaN
}
