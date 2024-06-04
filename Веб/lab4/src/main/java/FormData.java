import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class FormData {
	private int[] x;
	private int y;
	private int[] r;

	@Override
	public String toString() {
		return "CheckHitRequest{" +
				"x=" + Arrays.toString(x) +
				", y=" + y +
				", r=" + Arrays.toString(r) +
				'}';
	}
}
