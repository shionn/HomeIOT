package home.iot.db.dbo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Light {
	private String name;
	private String position;
	private List<Captor> captors;

	public Captor getState() {
		return captors.stream().filter(c -> c.getType() == CaptorType.LIGHT_STATE).findAny().orElse(null);
	}

	public boolean isOn() {
		return "on".equalsIgnoreCase(getState().getLastValue());
	}
}
