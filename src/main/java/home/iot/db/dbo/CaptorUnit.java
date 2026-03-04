package home.iot.db.dbo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CaptorUnit {
	Celcius("°"),
	None("");

	private final String symbol;
}
