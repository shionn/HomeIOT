package home.iot.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import home.iot.db.dao.CaptorDao;
import home.iot.db.dao.CaptorHistoryDao;
import home.iot.db.dbo.Captor;
import home.iot.db.dbo.CaptorValue;
import home.iot.home.dto.ChartData;
import home.iot.home.dto.ChartDataSets;

@Controller
public class CaptorHistoryController {

	@Autowired
	private SqlSession session;

	@GetMapping(path = "/captor/history/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody()
	@ResponseStatus(value = HttpStatus.OK)
	public ChartData read(@PathVariable("id") int id) {
		CaptorHistoryDao historyDao = session.getMapper(CaptorHistoryDao.class);
		List<CaptorValue> today = historyDao.readCurrentDay(id);
		List<CaptorValue> yesterday = historyDao.readYesterday(id);
		return map(session.getMapper(CaptorDao.class).read(id), today, yesterday);
	}

	private ChartData map(Captor captor, List<CaptorValue> today, List<CaptorValue> yesterday) {
//Stream.concat(today.stream(), yesterday.stream()).map(c->c.getDate())

		Map<String, CaptorValue> tMap = toMap(today);
		Map<String, CaptorValue> yMap = toMap(yesterday);
		return map(captor, tMap, yMap);
	}

	private ChartData map(Captor captor, Map<String, CaptorValue> today, Map<String, CaptorValue> yesterday) {
		List<String> labels = new ArrayList<String>();
		for (int h = 2; h < 26; h++) {
			for (int m = 0; m < 60; m += 10) {
				labels.add(String.format("%02d:%02d", h % 24, m));
			}
		}
		return ChartData.builder()
				.labels(labels)
				.datasets(Arrays.asList(
						ChartDataSets.builder()
								.label(captor.getName() + " Aujourd'hui")
								.data(labels.stream()
										.map(l -> today.get(l))
										.map(c -> c == null ? null : Float.valueOf(c.getValue()))
										.toList())
								.fill(false)
								.build(),
						ChartDataSets.builder()
								.label(captor.getName() + " Hier")
								.data(labels.stream()
										.map(l -> yesterday.get(l))
										.map(c -> c == null ? null : Float.valueOf(c.getValue()))
										.toList())
								.fill(false)
								.borderColor("#AAAAAA")
								.build())
						)
				.build();
	}

	private Map<String, CaptorValue> toMap(List<CaptorValue> today) {
		return today.stream()
				.collect(Collectors.toMap(c -> new SimpleDateFormat("HH:mm").format(c.getDate()), c -> c));
	}

}
