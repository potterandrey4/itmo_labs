package itmo.testing.function.trig

import itmo.testing.function.Calculable
import kotlin.math.abs

class Csc(private val sin: Calculable) : Calculable {
    override fun calculate(x: Double): Double {
        val sinVal = sin.calculate(x)
        if (sinVal.isNaN()) return Double.NaN
        if (abs(sinVal) < 1e-8) return 0.0
        return 1.0 / sinVal
    }
}