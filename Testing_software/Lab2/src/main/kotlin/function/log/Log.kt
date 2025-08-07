package itmo.testing.function.log

import itmo.testing.function.Calculable

class Log(val base: Double, private val ln: Calculable) : Calculable {
    override fun calculate(x: Double): Double {
        return ln.calculate(x) / ln.calculate(base)
    }
}
