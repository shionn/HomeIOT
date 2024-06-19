package home.iot.home.chart;

import java.util.List;
import java.util.function.Supplier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ChartAxeX {
	Hours(new ChartFormaters()::hours, "HH:mm"), Month(new ChartFormaters()::month, "dd/MM"),;

	private final Supplier<List<String>> labels;
	private final String dateFormat;

}
