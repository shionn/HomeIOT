package home.iot.captor.subscription;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import home.iot.db.dao.CaptorSubscriptionDao;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SubscriptionController {

	private final SqlSession session;
	private final SubscriptionNotifier notifier;

	@GetMapping(path = { "/subscriptions" })
	public ModelAndView subscriptions() {
		CaptorSubscriptionDao dao = session.getMapper(CaptorSubscriptionDao.class);
		return new ModelAndView("subscriptions").addObject("subscriptions", dao.listAllCaptorSubscription());
	}

	@GetMapping(path = { "/subscriptions/{id}/notify" })
	public String notify(@PathVariable("id") int id) {
		notifier.notifySubscription(id);
		return "redirect:/subscriptions";
	}

	@GetMapping(path = { "/subscriptions/{id}/remove" })
	public String remove(@PathVariable("id") int id) {
		CaptorSubscriptionDao dao = session.getMapper(CaptorSubscriptionDao.class);
		dao.removeSubscription(id);
		session.commit();
		return "redirect:/subscriptions";
	}

}
