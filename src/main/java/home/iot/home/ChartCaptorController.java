package home.iot.home;

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

import home.iot.db.dao.CaptorHistoryDao;
import home.iot.db.dbo.Captor;
import home.iot.db.dbo.CaptorValue;
import home.iot.home.chart.ChartDescription;
import home.iot.home.chart.ChartLineDescription;
import home.iot.home.dto.Chart;
import home.iot.home.dto.ChartData;
import home.iot.home.dto.ChartDataSets;

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
		List<String> labels = buildLabels();
		List<ChartDataSets> datas = new ArrayList<>();
		for (ChartLineDescription line : description.getLines()) {
			datas.add(buildChartDataSets(labels, line.getTitle(), toMap(line.getSource().apply(dao)), line.getColor()));
		}
		return Chart.builder()
				.title(description.getName() + " " + captor.getLastValue() + captor.getUnit().getSymbol())
				.data(ChartData.builder().labels(labels).datasets(datas).build())
				.build();
	}

	@GetMapping(path = "/chart/O11DW", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody()
	@ResponseStatus(value = HttpStatus.OK)
	public Chart o11dw() {
		CaptorHistoryDao dao = session.getMapper(CaptorHistoryDao.class);
		List<String> labels = buildLabels();
		List<ChartDataSets> datas = new ArrayList<>();
		datas.add(buildChartDataSets(labels, "CPU", toMap(dao.readCurrentDay(111)), "#0000AA"));
		datas.add(buildChartDataSets(labels, "GPU", toMap(dao.readCurrentDay(110)), "#AA0000"));
		Captor cpu = dao.readCaptor(111);
		Captor gpu = dao.readCaptor(110);
		return Chart.builder()
				.title("O11DW " + cpu.getLastValue() + cpu.getUnit().getSymbol() + " | " + gpu.getLastValue()
						+ gpu.getUnit().getSymbol())
				.data(ChartData.builder().labels(labels).datasets(datas).build())
				.build();

	}

	private List<String> buildLabels() {
		List<String> labels = new ArrayList<String>();
		for (int h = startOfDay(); h < 24 + startOfDay(); h++) {
			for (int m = 0; m < 60; m += 10) {
				labels.add(String.format("%02d:%02d", h % 24, m));
			}
		}
		return labels;
	}

	private int startOfDay() {
		// ici on definit le debut de la journee ete / hiver
		return 2;
	}

	private ChartDataSets buildChartDataSets(List<String> labels, String name, Map<String, CaptorValue> datas,
			String color) {
		return ChartDataSets.builder()
				.label(name)
				.data(labels.stream()
						.map(l -> datas.get(l))
						.map(c -> getCaptorFloatValue(c))
						.toList())
				.borderColor(color)
				.fill(false)
				.build();
	}

	private Float getCaptorFloatValue(CaptorValue c) {
		if (c == null || "nan".equals(c.getValue()))
			return null;
		return Float.valueOf(c.getValue());
	}

	private Map<String, CaptorValue> toMap(List<CaptorValue> data) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Map<String, CaptorValue> map = new HashMap<String, CaptorValue>();
		data.forEach(c -> map.put(format.format(c.getDate()), c));
		return map;
	}

}
