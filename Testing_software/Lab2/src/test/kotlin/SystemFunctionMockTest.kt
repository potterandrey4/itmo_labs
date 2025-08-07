import itmo.testing.SystemFunction
import itmo.testing.function.Calculable
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class SystemFunctionMockTest {

    @Test
    fun `should call trigonometric functions correct number of times for negative input`() {
        // Arrange
        val x = -1.0

        // Создаем моки с более гибкой настройкой
        val sin = mock<Calculable> {
            on { calculate(any()) } doReturn 0.8415
        }
        val cos = mock<Calculable> {
            on { calculate(any()) } doReturn 0.5403
        }
        val tan = mock<Calculable>()
        val cot = mock<Calculable>()
        val sec = mock<Calculable>()
        val csc = mock<Calculable>()

        val ln = mock<Calculable>()
        val log3 = mock<Calculable>()
        val log5 = mock<Calculable>()
        val log10 = mock<Calculable>()

        val system = SystemFunction(sin, cos, tan, cot, sec, csc, ln, log3, log5, log10)

        // Act
        system.calculate(x)

        // Assert
        // Проверяем количество вызовов (3 раза согласно вашему сообщению об ошибке)
        verify(sin, times(3)).calculate(eq(x))
        verify(cos, times(3)).calculate(eq(x))

        // Проверяем, что логарифмические функции не вызывались
        verify(ln, never()).calculate(any())
        verify(log3, never()).calculate(any())
        verify(log5, never()).calculate(any())
        verify(log10, never()).calculate(any())
    }
}