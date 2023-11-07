package beans;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataBean implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private List<ResponseBean> results;

	public DataBean() {
		this.results = new ArrayList<>();
	}

	public void add(ResponseBean result) {
		this.results.add(result);
	}

}
