package home.iot.captor.subscription;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import home.iot.db.dao.CaptorSubscriptionDao;
import home.iot.db.dbo.CaptorSubscription;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscriptionNotifier {

	private final SqlSession session;

	public void notify(int captorId) {
		CaptorSubscriptionDao dao = session.getMapper(CaptorSubscriptionDao.class);
		List<CaptorSubscription> list = dao.listOneCaptor(captorId);
		for (CaptorSubscription subscription:list) {
			notifySubscription(subscription);
		}
	}

	public void notifySubscription(int subscriptionId) {
		CaptorSubscriptionDao dao = session.getMapper(CaptorSubscriptionDao.class);
		notifySubscription(dao.readOneSubscription(subscriptionId));
	}

	private void notifySubscription(CaptorSubscription subscription) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest
					.newBuilder()
					.uri(URI.create("http://" + subscription.getHost() + "/captor/" + subscription.getCaptorId()))
					.header("Content-Type", "text/plain")
					.method("PUT", HttpRequest.BodyPublishers.ofString(subscription.getCaptor().getLastValue()))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (!response.body().equalsIgnoreCase("ok")) {
				System.out.println(response.body());
			}
		} catch (RuntimeException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
