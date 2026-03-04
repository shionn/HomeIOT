package home.iot.captor.temperature;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import home.iot.captor.chart.ChartDescription;

@Controller
public class TemperatureController {

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

}
