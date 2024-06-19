package home.iot.home.chart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartFormaters {

	public List<String> hours() {
		List<String> labels = new ArrayList<String>();
		for (int h = startOfDay(); h < 24 + startOfDay(); h++) {
			for (int m = 0; m < 60; m += 10) {
				labels.add(String.format("%02d:%02d", h % 24, m));
			}
		}
		return labels;
	}

	public List<String> month() {
		Calendar start = Calendar.getInstance();
		start.add(Calendar.MONTH, -1);
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DAY_OF_YEAR, 5);

		List<String> labels = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM");
		while (start.before(end)) {
			labels.add(format.format(start.getTime()));
			start.add(Calendar.DAY_OF_YEAR, 1);
		}
		return labels;
	}

	private int startOfDay() {
		// ici on definit le debut de la journee ete / hiver
		return 2;
	}

}
