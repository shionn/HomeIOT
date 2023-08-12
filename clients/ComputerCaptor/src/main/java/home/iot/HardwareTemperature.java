package home.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HardwareTemperature {

	private static final Pattern GPU_JUNCTION = Pattern.compile("junction:[^+]*\\+(\\d+\\.\\d+)°C");
	private static final Pattern CPU_TCTL = Pattern.compile("Tctl:[^+]*\\+(\\d+\\.\\d+)°C");
	private static final String COMMAND = "sensors amdgpu-pci-0d00 k10temp-pci-00c3";

	public static void main(String[] args) throws IOException {
		new HardwareTemperature().start();
	}

	private void start() throws IOException {
		try (InputStream is = Runtime.getRuntime().exec(COMMAND).getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				Matcher m = GPU_JUNCTION.matcher(line);
				if (m.find()) {
					System.out.println("found : " + m.group(1));
				}
				m = CPU_TCTL.matcher(line);
				if (m.find()) {
					System.out.println("found : " + m.group(1));
				}
				line = reader.readLine();
			}
		}

	}
}
