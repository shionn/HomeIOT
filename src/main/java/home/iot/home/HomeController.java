package home.iot.home;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import home.iot.db.dao.CaptorDao;

@Controller
public class HomeController {

	@Autowired
	private SqlSession sessionn;

	@GetMapping(path = "/")
	public ModelAndView home() {
		return new ModelAndView("home").addObject("captor", sessionn.getMapper(CaptorDao.class).read(100));
	}
}
