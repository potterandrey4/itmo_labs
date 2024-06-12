import java.util.function.Function;

public enum IntegralsEnum {
	INTEGRAL_1(x -> Math.pow(x, 3) / 3),
	INTEGRAL_2(x -> -Math.cos(x)),
	INTEGRAL_3(x -> Math.log(x)), // Предполагается, что log(x) доступен
	INTEGRAL_4(x -> x * Math.log(x + 1) + Math.log(x + 1) - x);

	private final Function<Double, Double> integral;

	IntegralsEnum(Function<Double, Double> integral) {
		this.integral = integral;
	}

	public double calculate(double value) {
		return integral.apply(value);
	}

}
