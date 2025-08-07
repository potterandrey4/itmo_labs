package itmo.testing.function.stub

import itmo.testing.function.Calculable
import kotlin.math.sin

class CscStub : Calculable {
    override fun calculate(x: Double): Double = when (x) {
        0.0 -> Double.NaN
        0.52359877559 -> 2.0
        0.78539816339 -> 1.4142
        1.0471975512 -> 1.1547
        1.57079632679 -> 1.0
        3.14159265359 -> Double.NaN
        4.71238898038 -> -1.0
        6.28318530718 -> Double.NaN
        -5.865620 -> 2.465871
        -5.793380 -> 2.125606
        -5.338170 -> 1.233797
        -3.784570 -> 1.667832
        -3.651740 -> 2.047897
        -3.560350 -> 2.459265
        -4.712390 -> 1.000000
        -4.480590 -> 1.027480
        -2.591950 -> -1.914306
        -2.570900 -> -1.851115
        -2.597659 -> -1.932345
        -5.79338, -5.33817, -3.78457, -3.65174, -3.56035 -> 1.0 / sin(x)
        else -> Double.NaN
    }
}
