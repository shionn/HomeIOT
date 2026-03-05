package home.iot.captor.subscription;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import home.iot.db.dao.CaptorSubscriptionDao;
import home.iot.db.dbo.CaptorSubscription;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscriptionNotifier {

	private final SubscriptionNotifierTask task;
	private final SqlSession session;

	public void notify(int captorId) {
		CaptorSubscriptionDao dao = session.getMapper(CaptorSubscriptionDao.class);
		List<CaptorSubscription> list = dao.listOneCaptor(captorId);
		// TODO amelioer pour que ce soit le for qui soit threader
		for (CaptorSubscription subscription : list) {
			task.notifySubscription(subscription);
		}
	}

	public void notifySubscription(int subscriptionId) {
		CaptorSubscriptionDao dao = session.getMapper(CaptorSubscriptionDao.class);
		task.notifySubscription(dao.readOneSubscription(subscriptionId));
	}

}
