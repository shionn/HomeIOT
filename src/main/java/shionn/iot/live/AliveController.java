package shionn.iot.live;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AliveController {

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, path = "/alive", produces = MediaType.APPLICATION_JSON_VALUE)
	public SimpleObject greeting() {
		return new SimpleObject("alive");
	}
	
}
