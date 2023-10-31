package home.iot.db.dbo;

import java.util.Date;

import lombok.Data;

@Data
public class Captor {

	private int id;
	private String name;
	private String position;
	private CaptorType type;
	private CaptorUnit unit;
	private String lastValue;
	private Date lastValueDate;
	private CaptorValue max;

}
