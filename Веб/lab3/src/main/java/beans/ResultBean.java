package beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@ToString
public class ResultBean implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false) @Setter @Getter
	private @NotNull Double x;
	@Column(nullable = false) @Setter @Getter
	private @NotNull Double y;
	@Column(nullable = false) @Setter @Getter
	private @NotNull Double r;

	@Column(nullable = false) @Setter @Getter
	private @NotNull String time;

	@Column(nullable = false) @Setter @Getter
	private @NotNull String executionTime;

	@Column(nullable = false) @Setter
	private @NotNull boolean isHit;

	public ResultBean() {}

	public ResultBean(ResultBean result) {
		this.x = result.x;
		this.y = result.y;
		this.r = result.r;
	}

	public boolean getIsHit() {
		return isHit;
	}

	@PrePersist
	protected void prePersist() {
		this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		this.isHit = checkHit();
		this.setExecutionTime("someDefaultValue"); // Замените "someDefaultValue" на фактическое значение, которое вы хотите установить
	}

	private boolean checkHit() {
		boolean area1_hit = x >= 0 && y >= 0 && x <= r && y <= r / 2;
		boolean area2_hit = x >= 0 && y <= 0 && y >= x - r / 2;
		boolean area3_hit = x <= 0 && y >= 0 && x * x + y * y <= r * r;
		return area1_hit || area2_hit || area3_hit;
	}
}
