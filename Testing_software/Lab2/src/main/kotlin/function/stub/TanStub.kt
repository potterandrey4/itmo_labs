package itmo.testing.function.stub

import itmo.testing.function.Calculable
import kotlin.math.tan

class TanStub : Calculable {
    override fun calculate(x: Double): Double = when (x) {
        0.0 -> 0.0
        0.52359877559 -> 0.5774
        0.78539816339 -> 1.0
        1.0471975512 -> 1.732
        1.57079632679 -> Double.NaN
        3.14159265359 -> 0.0
        -5.865620 -> 0.443655
        -5.793380 -> 0.533138
        -5.338170 -> 1.383752
        -3.784570 -> -0.749182
        -3.651740 -> -0.559552
        -3.560350 -> -0.445083
        -4.712390 -> 980762.048172
        -4.480590 -> -4.236538
        -2.591950 -> 0.612614
        -2.570900 -> 0.641946
        -2.597659 -> 0.604789
//        -5.79338, -5.33817, -3.78457, -3.65174, -3.56035 -> tan(x)
        else -> Double.NaN
    }
}
