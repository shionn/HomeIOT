package home.iot;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HardwareTemperature {

	public static void main(String[] args) throws IOException, InterruptedException {
		new HardwareTemperature().start(Mode.RAW);
	}

	private void start(Mode mode) throws IOException, InterruptedException {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		switch (mode) {
			case AVERAGE:
				new AverageCaptor().submit(service);
				break;
			case RAW:
			default:
				new RawCaptor().submit(service);
		}
		service.awaitTermination(1000, TimeUnit.DAYS);
	}

}
