package itmo.testing

import itmo.testing.function.Calculable
import java.io.File
import kotlin.math.pow

class SystemFunction(
    private val sin: Calculable,
    private val cos: Calculable,
    private val tan: Calculable,
    private val cot: Calculable,
    private val sec: Calculable,
    private val csc: Calculable,
    private val ln: Calculable,
    private val log3: Calculable,
    private val log5: Calculable,
    private val log10: Calculable
) {
    fun calculate(x: Double): Double {
        return if (x <= 0) {
            val numerator = ((((((((sec.calculate(x) * csc.calculate(x)) / csc.calculate(x)) - tan.calculate(x)).pow(3)).pow(3)) * ((cot.calculate(x) + tan.calculate(x)).pow(3))) - sin.calculate(x)))
            val denominator = ((((cos.calculate(x) - sin.calculate(x)).pow(2) - (sec.calculate(x).pow(2))) * tan.calculate(x)) * tan.calculate(x))
            val firstPart = numerator / denominator / sin.calculate(x)

            val secondPart = sec.calculate(x) /
                    ((((cot.calculate(x) + cos.calculate(x)) + (((sec.calculate(x).pow(2)) * csc.calculate(x)) + (cot.calculate(x) / tan.calculate(x)))) + cos.calculate(x)) * cot.calculate(x))

            firstPart + secondPart
        } else {
            val part1 = (((log3.calculate(x) - log3.calculate(x)) / log3.calculate(x)).pow(3)) - log5.calculate(x)
            val part2 = log5.calculate(x) / (log10.calculate(x) - log5.calculate(x).pow(2))
            part1 * part2
        }
    }

    fun exportToCsv(filename: String, from: Double, to: Double, step: Double) {
        val file = File(filename)
        file.bufferedWriter().use { writer ->
            writer.write("x;f(x)\n")
            var x = from
            while (x <= to) {
                try {
                    val value = calculate(x)
                    writer.write("$x;$value\n")
                } catch (e: Exception) {
                    writer.write("$x;NaN\n")
                }
                x += step
            }
        }
    }
}
