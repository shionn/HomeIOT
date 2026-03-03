package home.iot.home;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import home.iot.db.dao.CaptorSubscriptionDao;
import home.iot.home.chart.ChartDescription;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final SqlSession session;

	@GetMapping(path = { "/temperatures" })
	public String home() {
		return "redirect:/temperatures/" + ChartDescription.O11DW;
	}

	@GetMapping(path = { "/temperatures/{id}" })
	public ModelAndView temperatures(@PathVariable("id") ChartDescription description) {
		return new ModelAndView("temperatures")
				.addObject("descriptions", ChartDescription.values())
				.addObject("description", description);
	}

	@GetMapping(path = { "/", "/subscriptions" })
	public ModelAndView subscriptions() {
		CaptorSubscriptionDao dao = session.getMapper(CaptorSubscriptionDao.class);
		return new ModelAndView("subscriptions").addObject("subscriptions", dao.list());
	}

}
