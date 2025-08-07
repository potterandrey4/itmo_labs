package itmo.testing.function.stub

import itmo.testing.function.Calculable
import kotlin.math.cos

class SecStub : Calculable {
    override fun calculate(x: Double): Double = when (x) {
        0.0 -> 1.0
        1.57079632679 -> Double.NaN
        3.14159265359 -> -1.0
        4.71238898038 -> Double.NaN
        6.28318530718 -> 1.0
        -5.865620 -> 1.093997
        -5.793380 -> 1.133241
        -5.338170 -> 1.707270
        -3.784570 -> -1.249509
        -3.651740 -> -1.145905
        -3.560350 -> -1.094577
        -4.712390 -> 980762.048172
        -4.480590 -> -4.352960
        -2.591950 -> -1.172730
        -2.570900 -> -1.188316
        -2.597659 -> -1.168662
        -5.79338, -5.33817, -3.78457, -3.65174, -3.56035 -> 1.0 / cos(x)
        else -> Double.NaN
    }
}
