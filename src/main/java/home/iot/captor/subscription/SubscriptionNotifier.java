package home.iot.captor.subscription;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import home.iot.db.SessionFactory;
import home.iot.db.dao.CaptorSubscriptionDao;
import home.iot.db.dbo.CaptorSubscription;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscriptionNotifier {

	private SessionFactory factory = new SessionFactory();

	@Async
	public void notify(int captorId) {
		try (SqlSession session = factory.open()) {
			CaptorSubscriptionDao dao = session.getMapper(CaptorSubscriptionDao.class);
			List<CaptorSubscription> list = dao.listOneCaptor(captorId);
			for (CaptorSubscription subscription : list) {
				notifySubscription(dao, subscription);
			}
		}
	}

	@Async
	public void notifySubscription(int subscriptionId) {
		try (SqlSession session = factory.open()) {
			CaptorSubscriptionDao dao = session.getMapper(CaptorSubscriptionDao.class);
			notifySubscription(dao, dao.readOneSubscription(subscriptionId));
		}
	}

	private void notifySubscription(CaptorSubscriptionDao dao, CaptorSubscription subscription) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest
					.newBuilder()
					.uri(URI.create("http://" + subscription.getHost() + "/captor/" + subscription.getCaptorId()))
					.header("Content-Type", "text/plain")
					.method("PUT", HttpRequest.BodyPublishers.ofString(subscription.getCaptor().getLastValue()))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			// TODO generer les erreur.
			if (!response.body().equalsIgnoreCase("ok")) {
				System.out.println(response.body());

			}
		} catch (RuntimeException | IOException | InterruptedException e) {
			// TODO generer les erreur.
			e.printStackTrace();
		}
	}

}
