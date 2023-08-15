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
import home.iot.home.dto.Chart;
import home.iot.home.dto.ChartData;
import home.iot.home.dto.ChartDataSets;

@Controller
public class ChartCaptorController {

	@Autowired
	private SqlSession session;

	@GetMapping(path = "/chart/roomtemp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody()
	@ResponseStatus(value = HttpStatus.OK)
	public Chart read(@PathVariable("id") int id) {
		CaptorHistoryDao dao = session.getMapper(CaptorHistoryDao.class);
		Captor captor = dao.readCaptor(id);
		List<String> labels = buildLabels();
		List<ChartDataSets> datas = new ArrayList<>();
		datas.add(buildChartDataSets(labels, "Aujourd'hui", toMap(dao.readCurrentDay(id)), "#000000"));
		datas.add(buildChartDataSets(labels, "Hier", toMap(dao.readYesterday(id)), "#AAAAAA"));
//		if (id == 100) {
//			datas.add(buildChartDataSets(labels, "CPU", toMap(dao.readCurrentDay(111)), "#0000AA"));
//			datas.add(buildChartDataSets(labels, "GPU", toMap(dao.readCurrentDay(110)), "#AA0000"));
//		}
		return Chart.builder()
				.title(captor.getName() + " " + captor.getLastValue() + captor.getUnit().getSymbol())
				.data(ChartData.builder().labels(labels).datasets(datas).build())
				.build();
	}

	private List<String> buildLabels() {
		List<String> labels = new ArrayList<String>();
		for (int h = 2; h < 26; h++) {
			for (int m = 0; m < 60; m += 10) {
				labels.add(String.format("%02d:%02d", h % 24, m));
			}
		}
		return labels;
	}

	private ChartDataSets buildChartDataSets(List<String> labels, String name, Map<String, CaptorValue> datas,
			String color) {
		return ChartDataSets.builder()
				.label(name)
				.data(labels.stream()
						.map(l -> datas.get(l))
						.map(c -> c == null ? null : Float.valueOf(c.getValue()))
						.toList())
				.borderColor(color)
				.fill(false)
				.build();
	}

	private Map<String, CaptorValue> toMap(List<CaptorValue> data) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Map<String, CaptorValue> map = new HashMap<String, CaptorValue>();
		data.forEach(c -> map.put(format.format(c.getDate()), c));
		return map;
	}

}
