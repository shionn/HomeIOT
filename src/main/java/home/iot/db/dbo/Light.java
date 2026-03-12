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
		return getCaptor(CaptorType.LIGHT_STATE);
	}

	public Captor getHsv() {
		return getCaptor(CaptorType.LIGHT_HSV);
	}

	public String getCssHsv() {
		Captor captor = getCaptor(CaptorType.LIGHT_HSV);
		if (captor == null) {
			return "hsv(0, 100%, 100%, 1);";
		}
		String value = captor.getLastValue();
		int hue = Integer.parseUnsignedInt(value.substring(0, 4), 16);
		int sat = Integer.parseUnsignedInt(captor.getLastValue().substring(4, 6), 16);
		int val = Integer.parseUnsignedInt(captor.getLastValue().substring(6, 8), 16);
		hue = hue * 360 / 65535;
		sat = sat * 100 / 255;
		val = val * 100 / 255;
		return "hsv(" + hue + "," + sat + "%," + val + "%);";

	}

	public boolean isOn() {
		return "on".equalsIgnoreCase(getState().getLastValue());
	}

	private Captor getCaptor(CaptorType type) {
		return captors.stream().filter(c -> c.getType() == type).findAny().orElse(null);
	}

}
