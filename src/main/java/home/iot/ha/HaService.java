package home.iot.ha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HaService {

	@Value("${homeassistant.url}")
	private String homeAssistantUrl;

	@Value("${homeassistant.token}")
	private String homeAssistantToken;

	private final RestTemplate restTemplate = new RestTemplate();

	public String getAllData() {
		String endpoint = homeAssistantUrl + "/api/states";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + homeAssistantToken);
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new IllegalStateException("Failed to retrieve data: " + response.getStatusCode());
		}
	}

	public String getSensorDataRaw(String sensorEntityId) {
		String endpoint = homeAssistantUrl + "/api/states/" + sensorEntityId;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + homeAssistantToken);
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new IllegalStateException("Failed to retrieve data: " + response.getStatusCode());
		}
	}

	public SensorState getSensorData(String sensorEntityId) {
		String endpoint = homeAssistantUrl + "/api/states/" + sensorEntityId;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + homeAssistantToken);
		headers.set("Content-Type", "application/json");

		HttpEntity<SensorState> entity = new HttpEntity<>(headers);
		ResponseEntity<SensorState> response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, SensorState.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new IllegalStateException("Failed to retrieve data: " + response.getStatusCode());
		}
	}

}