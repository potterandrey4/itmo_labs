package itmo.testing

import itmo.testing.function.log.*
import itmo.testing.function.trig.*

fun main() {
    val sin = Sin()
    val cos = Cos(sin)
    val tan = Tan(sin, cos)
    val cot = Cot(sin, cos)
    val sec = Sec(cos)
    val csc = Csc(sin)
    val ln = Ln()
    val log3 = Log(3.0, ln)
    val log5 = Log(5.0, ln)
    val log10 = Log(10.0, ln)

    val system = SystemFunction(
        sin, cos, tan, cot, sec, csc, ln, log3, log5, log10
    )

    system.exportToCsv("build/system_function.csv", -6.0, 6.0, 0.05)
}
// 32