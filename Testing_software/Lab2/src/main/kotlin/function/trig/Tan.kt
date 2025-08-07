package itmo.testing.function.trig

import itmo.testing.function.Calculable

class Tan(private val sin: Calculable, private val cos: Calculable) : Calculable {
    override fun calculate(x: Double): Double {
        val cosVal = cos.calculate(x)
        if (cosVal == 0.0) throw ArithmeticException("tan(x) undefined for x = $x")
        return sin.calculate(x) / cosVal
    }
}