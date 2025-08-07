package itmo.testing.function.trig

import itmo.testing.function.Calculable

class Cot(private val sin: Calculable, private val cos: Calculable) : Calculable {
    override fun calculate(x: Double): Double {
        val sinVal = sin.calculate(x)
        if (sinVal == 0.0) throw ArithmeticException("cot(x) undefined for x = $x")
        return cos.calculate(x) / sinVal
    }
}