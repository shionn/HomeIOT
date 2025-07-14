package home.iot.ha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HaController {

	@Autowired
	private HaService service;

	@GetMapping("/ha")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public String getAll() {
		return service.getAllData();
	}

	@GetMapping("/ha/{id}")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public String getLastValue(@PathVariable("id") String id) {
		return service.getSensorData(id).getState();
	}
	
}
