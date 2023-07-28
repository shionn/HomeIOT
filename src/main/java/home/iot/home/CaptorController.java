package home.iot.home;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import home.iot.db.dao.CaptorDao;

@Controller
public class CaptorController {

	@Autowired
	private Logger logger;

	@Autowired
	private SqlSession session;

	@PutMapping(path = "/captor/{id}")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public void update(@PathVariable("id") int id, @RequestBody() String value) {
		logger.info("Receive " + id + " : " + value);
		CaptorDao dao = session.getMapper(CaptorDao.class);
		dao.insertValue(id, value);
		dao.updateLastValue(id, value);
		session.commit();
	}

	@GetMapping(path = "/captor/{id}")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public String read(@PathVariable("id") int id) {
		return session.getMapper(CaptorDao.class).read(id).getLastValue();
	}

}
