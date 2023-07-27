package home.iot.home;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import home.iot.db.dao.CaptorDao;

@Controller
public class CaptorController {

	@Autowired
	private SqlSession session;

	@PutMapping(path = "/captor/{id}")
	public void update(@PathVariable("id") int id, @RequestBody() String value) {
		System.out.println(id + " :: " + value);
		session.getMapper(CaptorDao.class).updateLastValue(id, value);
		session.commit();
	}

}
