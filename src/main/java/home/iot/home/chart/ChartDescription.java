package home.iot.home.chart;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
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
							.build()),
			ChartAxeX.Hours),
	BureauMonth(
			"Bureau (30J)",
			100,
			Arrays.asList(
					ChartLineDescription.builder()
							.title("Max")
							.source(dao -> dao.readLastMonth(100, "max"))
							.color("#FF0000")
							.build(),
					ChartLineDescription.builder()
							.title("Min")
							.source(dao -> dao.readLastMonth(100, "min"))
							.color("#0000FF")
							.build(),
					ChartLineDescription.builder()
							.title("Max")
							.source(dao -> dao.readLastYearMonth(100, "max"))
							.color("#AA0000")
							.build(),
					ChartLineDescription.builder()
							.title("Min")
							.source(dao -> dao.readLastYearMonth(100, "min"))
							.color("#0000AA")
							.build()),
			ChartAxeX.Month),
	O11DW(
			"O11DW",
			111,
			Arrays.asList(
					ChartLineDescription.builder()
							.title("CPU")
							.source(dao -> dao.readCurrentDay(111))
							.color("#0000AA")
							.build(),
					ChartLineDescription.builder()
							.title("GPU")
							.source(dao -> dao.readCurrentDay(110))
							.color("#AA0000")
							.build(),
					ChartLineDescription.builder()
							.title("SN850X 2To")
							.source(dao -> dao.readCurrentDay(112))
							.color("#00AA00")
							.build()),
			ChartAxeX.Hours),
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
							.build()),
			ChartAxeX.Hours),
	ChambreMorganMonth(
			"Chambre Morgan (30J)",
			101,
			Arrays.asList(
					ChartLineDescription.builder()
							.title("Max")
							.source(dao -> dao.readLastMonth(101, "max"))
							.color("#FF0000")
							.build(),
					ChartLineDescription.builder()
							.title("Min")
							.source(dao -> dao.readLastMonth(101, "min"))
							.color("#0000FF")
							.build(),
					ChartLineDescription.builder()
							.title("Max")
							.source(dao -> dao.readLastYearMonth(101, "max"))
							.color("#AA0000")
							.build(),
					ChartLineDescription.builder()
							.title("Min")
							.source(dao -> dao.readLastYearMonth(101, "min"))
							.color("#0000AA")
							.build()),
			ChartAxeX.Month),


	;

	private String name;
	private int id;
	private List<ChartLineDescription> lines;
	private ChartAxeX axeX;

	private ChartDescription(String name, int id, List<ChartLineDescription> lines, ChartAxeX axeX) {
		this.name = name;
		this.id = id;
		this.lines = lines;
		this.axeX = axeX;
	}

}
