package itmo.testing.function.log

import itmo.testing.function.Calculable
import kotlin.math.abs

class Ln(private val eps: Double = 1e-10) : Calculable {
    override fun calculate(x: Double): Double {
        if (x <= 0) throw IllegalArgumentException("ln(x) undefined for x <= 0")
        if (x == 1.0) return 0.0

        var y = (x - 1) / (x + 1)
        var term = y
        var sum = 0.0
        var n = 1

        while (abs(term) > eps) {
            sum += term / n
            term *= y * y
            n += 2
        }

        return 2 * sum
    }
}