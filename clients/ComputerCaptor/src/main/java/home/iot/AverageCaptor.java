package home.iot;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AverageCaptor {
	private BigDecimal cpuTemp = BigDecimal.ZERO;
	private BigDecimal gpuTemp = BigDecimal.ZERO;
	private BigDecimal nvme0Temp = BigDecimal.ZERO;
	private BigDecimal nvme1Temp = BigDecimal.ZERO;

	private int countTemp = 0;

	private LmSensorReader reader = new LmSensorReader();

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
		}, 10, 10, TimeUnit.MINUTES);

	}

	private void read() {
		String value = reader.read(Consts.COMMAND_CPU, Consts.CPU_TCTL);
		if (value != null) {
			cpuTemp = cpuTemp.add(new BigDecimal(value));
		}
		value = reader.read(Consts.COMMAND_GPU, Consts.GPU_JUNCTION);
		if (value != null) {
			gpuTemp = gpuTemp.add(new BigDecimal(value));
		}
		value = reader.read(Consts.COMMAND_NVME0, Consts.COMPOSITE);
		if (value != null) {
			nvme0Temp = nvme0Temp.add(new BigDecimal(value));
		}
		value = reader.read(Consts.COMMAND_NVME1, Consts.COMPOSITE);
		if (value != null) {
			nvme1Temp = nvme1Temp.add(new BigDecimal(value));
		}
		System.out.println(
				countTemp + " cpu " + cpuTemp + " gpu " + gpuTemp + " nvme0 " + nvme0Temp + " nvme1 " + nvme1Temp);
		countTemp++;
	}

	private void send() {
		if (countTemp > 0) {
			try {
				String cpu = cpuTemp.divide(BigDecimal.valueOf(countTemp), 1, RoundingMode.UP).toString();
				String gpu = gpuTemp.divide(BigDecimal.valueOf(countTemp), 1, RoundingMode.UP).toString();
				String nvme0 = nvme0Temp.divide(BigDecimal.valueOf(countTemp), 1, RoundingMode.UP).toString();
				String nvme1 = nvme1Temp.divide(BigDecimal.valueOf(countTemp), 1, RoundingMode.UP).toString();
				send(Consts.CPU_CAPTOR, cpu);
				send(Consts.GPU_CAPTOR, gpu);
				send(Consts.NVME0_CAPTOR, nvme0);
				send(Consts.NVME1_CAPTOR, nvme1);
				cpuTemp = BigDecimal.ZERO;
				gpuTemp = BigDecimal.ZERO;
				nvme0Temp = BigDecimal.ZERO;
				nvme1Temp = BigDecimal.ZERO;
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
