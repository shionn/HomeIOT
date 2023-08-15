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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HardwareTemperature {

	private static final String HOST = "http://homeiot/captor/";
//	private static final String HOST = "http://localhost:8080/HomeIOT/captor/";

	private static final Pattern GPU_JUNCTION = Pattern.compile("junction:[^+]*\\+(\\d+\\.\\d+)°C");
	private static final Pattern CPU_TCTL = Pattern.compile("Tctl:[^+]*\\+(\\d+\\.\\d+)°C");
	private static final String COMMAND = "sensors amdgpu-pci-0d00 k10temp-pci-00c3";

	public static void main(String[] args) throws IOException, InterruptedException {
		new HardwareTemperature().start();
	}

	private void start() throws IOException, InterruptedException {
		 ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				readAndSend();
			}
		}, 1, 10, TimeUnit.MINUTES);
		service.awaitTermination(1000, TimeUnit.DAYS);
	}

	protected void readAndSend() {
		try (InputStream is = Runtime.getRuntime().exec(COMMAND).getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			String line = reader.readLine();
			while (line != null) {
				Matcher m = GPU_JUNCTION.matcher(line);
				if (m.find()) {
					send(110, m.group(1));
				}
				m = CPU_TCTL.matcher(line);
				if (m.find()) {
					send(111, m.group(1));
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void send(int captor, String value) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(HOST + captor).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		try (OutputStream os = connection.getOutputStream(); OutputStreamWriter osw = new OutputStreamWriter(os)) {
			osw.write(value);
		}
		int code = connection.getResponseCode();
		System.out.println("send " + value + " for " + captor + " response " + code);
	}
}
