package itmo.testing.function.trig

import itmo.testing.function.Calculable

class Sec(private val cos: Calculable) : Calculable {
    override fun calculate(x: Double): Double {
        val cosVal = cos.calculate(x)
        if (cosVal == 0.0) throw ArithmeticException("sec(x) undefined for x = $x")
        return 1.0 / cosVal
    }
}