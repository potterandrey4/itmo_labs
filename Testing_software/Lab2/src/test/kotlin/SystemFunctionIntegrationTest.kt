import itmo.testing.SystemFunction
import itmo.testing.function.stub.*
import itmo.testing.function.trig.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

class SystemFunctionIntegrationTest {

    @Nested
    @DisplayName("Integration 1: всё заглушки")
    inner class Step1 {
        private val sin = SinStub()
        private val cos = CosStub()
        private val tan = TanStub()
        private val cot = CotStub()
        private val sec = SecStub()
        private val csc = CscStub()
        private val ln = LnStub()
        private val log3 = Log3Stub()
        private val log5 = Log5Stub()
        private val log10 = Log10Stub()

        private val system = SystemFunction(sin, cos, tan, cot, sec, csc, ln, log3, log5, log10)

        @ParameterizedTest
        @CsvFileSource(resources = ["/system_dots.csv"], numLinesToSkip = 1)
        fun testAllTrigStub(x: Double, expected: Double) {
            val actual = system.calculate(x)
            assertEquals(expected, actual, 1e-0)
        }
    }

    @Nested
    @DisplayName("Integration 2: sec, tan, cot реальные; sin, cos, csc заглушки")
    inner class Step2 {
        private val sin = SinStub()
        private val cos = CosStub()
        private val tan = Tan(sin, cos)
        private val cot = Cot(sin, cos)
        private val sec = Sec(cos)
        private val csc = CscStub()
        private val ln = LnStub()
        private val log3 = Log3Stub()
        private val log5 = Log5Stub()
        private val log10 = Log10Stub()

        private val system = SystemFunction(sin, cos, tan, cot, sec, csc, ln, log3, log5, log10)

        @ParameterizedTest
        @CsvFileSource(resources = ["/system_dots.csv"], numLinesToSkip = 1)
        fun testSecTanCotReal(x: Double, expected: Double) {
            val actual = system.calculate(x)
            assertEquals(expected, actual, 1e-1)
        }
    }

    @Nested
    @DisplayName("Integration 3: cos, csc реальное; sin заглушка")
    inner class Step3 {
        private val sin = SinStub()
        private val cos = Cos(sin)
        private val tan = TanStub()
        private val cot = CotStub()
        private val sec = SecStub()
        private val csc = Csc(sin)
        private val ln = LnStub()
        private val log3 = Log3Stub()
        private val log5 = Log5Stub()
        private val log10 = Log10Stub()

        private val system = SystemFunction(sin, cos, tan, cot, sec, csc, ln, log3, log5, log10)

        @ParameterizedTest
        @CsvFileSource(resources = ["/system_dots.csv"], numLinesToSkip = 1)
        fun testCosCscReal(x: Double, expected: Double) {
            val actual = system.calculate(x)
            assertEquals(expected, actual, 1e-1)
        }
    }

    @Nested
    @DisplayName("Integration 4: вся тригонометрия реальная")
    inner class Step4 {
        private val sin = Sin()
        private val cos = Cos(sin)
        private val tan = Tan(sin, cos)
        private val cot = Cot(sin, cos)
        private val sec = Sec(cos)
        private val csc = Csc(sin)
        private val ln = LnStub()
        private val log3 = Log3Stub()
        private val log5 = Log5Stub()
        private val log10 = Log10Stub()

        private val system = SystemFunction(sin, cos, tan, cot, sec, csc, ln, log3, log5, log10)

        @ParameterizedTest
        @CsvFileSource(resources = ["/system_dots.csv"], numLinesToSkip = 1)
        fun testAllTrigReal(x: Double, expected: Double) {
            val actual = system.calculate(x)
            assertEquals(expected, actual, 1e-3)
        }
    }

}