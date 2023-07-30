package home.iot.home;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

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
		return ChartData.builder()
				.labels(today.stream().map(v -> new SimpleDateFormat("HH:mm").format(v.getDate())).toList())
				.datasets(Arrays.asList(
						ChartDataSets.builder()
								.label(captor.getName() + " Aujourd'hui")
								.data(today.stream().map(v -> v.getValue()).map(Float::valueOf).toList())
								.fill(false)
								.build()/*
										 * , ChartDataSets.builder() .label(captor.getName() + " Hier")
										 * .data(yesterday.stream().map(v -> v.getValue()).map(Float::valueOf).toList())
										 * .fill(false) .build()
										 */)
						)
				.build();
	}

}
