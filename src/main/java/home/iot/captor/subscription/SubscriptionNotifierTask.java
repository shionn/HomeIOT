package home.iot.captor.subscription;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import home.iot.db.dbo.CaptorSubscription;

@Component
public class SubscriptionNotifierTask {

	@Async
	public void notifySubscription(CaptorSubscription subscription) {
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
