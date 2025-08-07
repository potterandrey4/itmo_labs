package itmo.testing.function.stub

import itmo.testing.function.Calculable

class SinStub : Calculable {
    override fun calculate(x: Double): Double = when (x) {
//      -5.865620 -> 0.405536
        -5.86562 -> 0.405536
        -5.79338 -> 0.470454
        -5.33817 -> 0.810506
        -3.78457 -> 0.599581
        -3.65174 -> 0.488306
        -3.56035 -> 0.406625
        -4.71239 -> 1.000000
        -4.48059 -> 0.973255
        -2.59195 -> -0.522383
        -2.57090 -> -0.540215
        -2.59765 -> -0.517506
        else -> Double.NaN
    }
}