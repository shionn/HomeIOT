package home.iot.captor.light;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import home.iot.captor.subscription.SubscriptionNotifier;
import home.iot.db.dao.CaptorDao;
import home.iot.db.dao.LightDao;
import home.iot.db.dbo.Light;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LightController {

	private final SqlSession session;
	private final SubscriptionNotifier notifier;

	@GetMapping("/lights")
	public ModelAndView listLights() {
		List<Light> lights = session.getMapper(LightDao.class).list();
		return new ModelAndView("lights") //
				.addObject("lights", lights) //
				.addObject("modes", Arrays.asList("static", "rainbow", "theatre", "breath", "crawl"));
	}

	@GetMapping("/lights/{id}/{value:.+}")
	public String update(@PathVariable("id") int id, @PathVariable("value") String value) {
		CaptorDao dao = session.getMapper(CaptorDao.class);
		if (value.startsWith("hsv")) {
			value = convertHsv(value);
		}
		dao.insertValue(id, value);
		dao.updateLastValue(id, value);
		session.commit();
		notifier.notify(id);
		return "redirect:/lights";
	}

	private String convertHsv(String value) {
		String[] split = value.replaceAll("[^0-9,]", "").split(",");
		return String
				.format("%04X%02X%02X", Integer.parseUnsignedInt(split[0]) * 65535 / 360, //
						Integer.parseUnsignedInt(split[1]) * 255 / 100, //
						Integer.parseUnsignedInt(split[2]) * 255 / 100);
	}

}
