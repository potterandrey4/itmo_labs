package itmo.testing.function.trig

import itmo.testing.function.Calculable
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow

class Sin(private val eps: Double = 1e-10) : Calculable {
    override fun calculate(x: Double): Double {
        val currentX = normalize(x)
        var term = currentX
        var sum = term
        var n = 1
        var sign = -1.0
        var power = currentX * currentX
        var fact = 1.0
        var i = 3
    
        while (abs(term) > eps) {
            fact *= (i - 1) * i
            term = sign * currentX.pow(i) / fact
            sum += term
            sign *= -1
            i += 2
        }
    
        return sum
    }
    

    // Приведение x к интервалу [-π, π] для повышения точности
    private fun normalize(x: Double): Double {
        var result = x % (2 * PI)
        if (result > PI) result -= 2 * PI
        if (result < -PI) result += 2 * PI
        return result
    }

    private fun factorial(n: Int): Long {
        var result = 1L
        for (i in 2..n) {
            result *= i
        }
        return result
    }
}
