package home.iot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LmSensorReader {

	public String read(String command, Pattern sensor) {
		try (InputStream is = Runtime.getRuntime().exec(command).getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			String line = reader.readLine();
			while (line != null) {
				Matcher m = sensor.matcher(line);
				if (m.find()) {
					return m.group(1);
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
