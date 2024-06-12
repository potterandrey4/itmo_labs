import java.util.function.Function;

public enum FunctionsEnum {
	FUNCTION_1(x -> x * x),
	FUNCTION_2(Math::sin),
	FUNCTION_3(x -> 1.0 / x),
	FUNCTION_4(x -> Math.log(x + 1));

	private final Function<Double, Double> function;

	FunctionsEnum(Function<Double, Double> function) {
		this.function = function;
	}

	public double calculate(double value) {
		return function.apply(value);
	}
}
