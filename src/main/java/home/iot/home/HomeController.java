package home.iot.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import home.iot.home.chart.ChartDescription;

@Controller
public class HomeController {

	@GetMapping(path = "/")
	public ModelAndView home() {
		return new ModelAndView("temp").addObject("descriptions", ChartDescription.values());
	}

	@GetMapping(path="/temp/{description}")
	public ModelAndView temp() {
		return new ModelAndView("temp").addObject("descriptions", ChartDescription.values());
	}
}
