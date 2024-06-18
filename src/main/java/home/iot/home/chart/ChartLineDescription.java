package home.iot.home.chart;

import java.util.List;
import java.util.function.Function;

import home.iot.db.dao.CaptorHistoryDao;
import home.iot.db.dbo.CaptorValue;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class ChartLineDescription {

	private final String title;
	private final Function<CaptorHistoryDao, List<CaptorValue>> source;
	private final String color;

}
