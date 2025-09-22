package home.iot;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class RawCaptor {

	private LmSensorReader reader = new LmSensorReader();
	private List<Captor> captors = new ArrayList<Captor>();

	public RawCaptor(Properties properties) {
		String computer = properties.getProperty("computer");
		for (String id : properties.getProperty("computer." + computer).split(",")) {
			captors
					.add(Captor
							.builder()
							.id(Integer.parseInt(properties.getProperty("computer." + computer + "." + id + ".id")))
							.command(properties.getProperty("computer." + computer + "." + id + ".command"))
							.sensor(Pattern
									.compile(properties.getProperty("computer." + computer + "." + id + ".sensor")))
							.build());
		}
	}

	public void submit(ScheduledExecutorService service) {
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				readAndSend();
			}
		}, 1, 1, TimeUnit.MINUTES);
	}

	private void readAndSend() {
		for (Captor captor : captors) {
			readAndSend(captor.getCommand(), captor.getSensor(), captor.getId());
		}
	}

	private void readAndSend(String command, Pattern sensor, int captor) {
		String value = reader.read(command, sensor);
		if (value != null) {
			try {
				send(captor, value);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void send(int captor, String value) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(Consts.HOST + captor).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		try (OutputStream os = connection.getOutputStream(); OutputStreamWriter osw = new OutputStreamWriter(os)) {
			osw.write(value);
		}
		int code = connection.getResponseCode();
		System.out.println("send " + value + " for " + captor + " response " + code);
	}

}
