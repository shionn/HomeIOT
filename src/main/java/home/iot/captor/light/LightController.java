package home.iot.captor.light;

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
		return new ModelAndView("lights").addObject("lights", lights);
	}

	@GetMapping("/lights/{id}/{value}")
	public String update(@PathVariable("id") int id, @PathVariable("value") String value) {
		CaptorDao dao = session.getMapper(CaptorDao.class);
		dao.insertValue(id, value);
		dao.updateLastValue(id, value);
		session.commit();
		notifier.notify(id);
		return "redirect:/lights";
	}

}
