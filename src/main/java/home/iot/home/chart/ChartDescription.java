package home.iot.home.chart;

import java.util.Arrays;
import java.util.List;

public enum ChartDescription {
	BureauDay(
			"Bureau",
			100,
			Arrays.asList(
					ChartLineDescription.builder()
							.title("Aujourd'hui")
							.source(dao -> dao.readCurrentDay(100))
							.color("#000000")
							.build(),
					ChartLineDescription.builder()
							.title("Hier")
							.source(dao -> dao.readYesterday(100))
							.color("#AAAAAA")
							.build())),
	BureauMonth(
			"Bureau (30J)",
			100,
			Arrays.asList(
					ChartLineDescription.builder()
							.title("Max")
							.source(dao -> dao.readLastMonth(100, "max"))
							.color("#000000")
							.build(),
					ChartLineDescription.builder()
							.title("Min")
							.source(dao -> dao.readLastMonth(100, "min"))
							.color("#000000")
							.build())),
	ChambreMorgan(
			"Chambre Morgan",
			101,
			Arrays.asList(
					ChartLineDescription.builder()
							.title("Aujourd'hui")
							.source(dao -> dao.readCurrentDay(101))
							.color("#000000")
							.build(),
					ChartLineDescription.builder()
							.title("Hier")
							.source(dao -> dao.readYesterday(101))
							.color("#AAAAAA")
							.build())),


	;

	private String name;
	private int id;
	private List<ChartLineDescription> lines;

	private ChartDescription(String name, int id, List<ChartLineDescription> lines) {
		this.name = name;
		this.id = id;
		this.lines = lines;

	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public List<ChartLineDescription> getLines() {
		return lines;
	}

}
