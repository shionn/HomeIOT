package home.iot;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class RawCaptor {

	private LmSensorReader reader = new LmSensorReader();


	public void submit(ScheduledExecutorService service) {
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				readAndSend();
			}
		}, 1, 1, TimeUnit.MINUTES);
	}

	private void readAndSend() {
		readAndSend(Consts.COMMAND_CPU, Consts.CPU_TCTL, Consts.CPU_CAPTOR);
		readAndSend(Consts.COMMAND_GPU, Consts.GPU_EDGE, Consts.GPU_CAPTOR);
		readAndSend(Consts.COMMAND_GPU, Consts.GPU_JUNCTION, Consts.GPU_CAPTOR_JUNCTION);
		readAndSend(Consts.COMMAND_NVME, Consts.COMPOSITE, Consts.NVME_CAPTOR);
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
