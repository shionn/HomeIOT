package home.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class AverageCaptor {
	private BigDecimal cpuTemp = BigDecimal.ZERO;
	private BigDecimal gpuTemp = BigDecimal.ZERO;
	private int countTemp = 0;

	public void submit(ScheduledExecutorService service) {
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				read();
			}
		}, 1, 1, TimeUnit.MINUTES);
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				send();
			}
		}, 1, 10, TimeUnit.MINUTES);

	}

	private void read() {
		try (InputStream is = Runtime.getRuntime().exec(Consts.COMMAND).getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			String line = reader.readLine();
			while (line != null) {
				Matcher m = Consts.GPU_JUNCTION.matcher(line);
				if (m.find()) {
					gpuTemp = gpuTemp.add(new BigDecimal(m.group(1)));
				}
				m = Consts.CPU_TCTL.matcher(line);
				if (m.find()) {
					cpuTemp = cpuTemp.add(new BigDecimal(m.group(1)));
				}
				line = reader.readLine();
			}
			System.out.println(countTemp + " cpu " + cpuTemp + " gpu " + gpuTemp);
			countTemp++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void send() {
		if (countTemp > 0) {
			try {
				String cpu = cpuTemp.divide(BigDecimal.valueOf(countTemp), 1, RoundingMode.UP).toString();
				String gpu = gpuTemp.divide(BigDecimal.valueOf(countTemp), 1, RoundingMode.UP).toString();
				send(Consts.CPU_CAPTOR, cpu);
				send(Consts.GPU_CAPTOR, gpu);
				countTemp = 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Aucune lecture");
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
