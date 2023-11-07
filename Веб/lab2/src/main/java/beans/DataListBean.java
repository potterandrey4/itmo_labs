package beans;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataListBean implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private List<DataBean> results;

	public DataListBean() {
		this.results = new ArrayList<>();
	}



	public void add(DataBean result) {
		this.results.add(result);
	}

	public List<DataBean> getResults() {
		return results;
	}

	public static class DataBean implements Serializable {
		@Serial
		private static final long serialVersionUID = 2L;
		private String x;
		private String y;
		private String r;
		private String isHit;
		private String executionTime;
		private String time;

		public DataBean(int x, double y, int r, boolean isHit, String executionTime, String time) {
			this.x = String.valueOf(x);
			this.y = String.valueOf(y);
			this.r = String.valueOf(r);
			this.isHit = String.valueOf(isHit);
			this.executionTime = executionTime;
			this.time = time;
		}

		public String getX() {
			return x;
		}

		public String getY() {
			return y;
		}

		public String getR() {
			return r;
		}

		public String getIsHit() {
			if (isHit.equals("true")) {
				return "Попал";
			}
			return "Мимо";
		}

		public String getExecutionTime() {
			return executionTime;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			DataBean result = (DataBean) o;
			return isHit == result.isHit &&
					x.equals(result.x) &&
					y.equals(result.y) &&
					r.equals(result.r);
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y, r, isHit);
		}

		@Override
		public String toString() {
			return "<tr>" +
						"<td>" + x + "</td>" +
						"<td>" + y + "</td>" +
						"<td>" + r + "/<td>" +
						"<td>" + isHit + "</td>"+
						"<td>" + executionTime + " мс</td>" +
						"<td>" + time + "</td>" +
					"</tr>";
		}

		public String getTime() {
			return time;
		}
	}
}
