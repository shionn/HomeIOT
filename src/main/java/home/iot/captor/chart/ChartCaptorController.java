package home.iot.captor.chart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import home.iot.captor.chart.dto.Chart;
import home.iot.captor.chart.dto.ChartData;
import home.iot.captor.chart.dto.ChartDataSets;
import home.iot.db.dao.CaptorHistoryDao;
import home.iot.db.dbo.Captor;
import home.iot.db.dbo.CaptorValue;

@Controller
public class ChartCaptorController {

	@Autowired
	private SqlSession session;

	@GetMapping(path = "/chart/{description}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Chart read(@PathVariable("description") ChartDescription description) {
		CaptorHistoryDao dao = session.getMapper(CaptorHistoryDao.class);
		Captor captor = dao.readCaptor(description.getId());
		List<String> labels = description.getAxeX().getLabels().get();
		List<ChartDataSets> datas = new ArrayList<>();
		for (ChartLineDescription line : description.getLines()) {
			datas.add(buildChartDataSets(labels, line.getTitle(),
					toMap(line.getSource().apply(dao), description.getAxeX()), line.getColor()));
		}
		return Chart.builder()
				.title(description.getName() + " " + captor.getLastValue() + captor.getUnit().getSymbol())
				.data(ChartData.builder().labels(labels).datasets(datas).build())
				.build();
	}

	private ChartDataSets buildChartDataSets(List<String> labels, String name, Map<String, CaptorValue> datas,
			String color) {
		return ChartDataSets.builder()
				.label(name)
				.data(labels.stream().map(l -> datas.get(l)).map(c -> getCaptorFloatValue(c)).toList())
				.borderColor(color)
				.fill(false)
				.build();
	}

	private Float getCaptorFloatValue(CaptorValue c) {
		if (c == null || "nan".equals(c.getValue()))
			return null;
		return Float.valueOf(c.getValue());
	}

	private Map<String, CaptorValue> toMap(List<CaptorValue> data, ChartAxeX axeX) {
		SimpleDateFormat format = new SimpleDateFormat(axeX.getDateFormat());
		Map<String, CaptorValue> map = new HashMap<String, CaptorValue>();
		data.forEach(c -> map.put(format.format(c.getDate()), c));
		return map;
	}

}
