package home.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class RawCaptor {


	public void submit(ScheduledExecutorService service) {
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				readAndSend();
			}
		}, 1, 10, TimeUnit.MINUTES);
	}

	private void readAndSend() {
		try (InputStream is = Runtime.getRuntime().exec(Consts.COMMAND).getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			String line = reader.readLine();
			while (line != null) {
				Matcher m = Consts.GPU_JUNCTION.matcher(line);
				if (m.find()) {
					send(Consts.GPU_CAPTOR, m.group(1));
				}
				m = Consts.CPU_TCTL.matcher(line);
				if (m.find()) {
					send(Consts.CPU_CAPTOR, m.group(1));
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
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
