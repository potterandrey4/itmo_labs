package itmo.testing.function.trig

import itmo.testing.function.Calculable
import kotlin.math.PI

class Cos(private val sin: Calculable) : Calculable {
    override fun calculate(x: Double): Double {
        println(x)
        println(sin.calculate(PI / 2 - x))
        return sin.calculate(PI / 2 - x)
    }
}
