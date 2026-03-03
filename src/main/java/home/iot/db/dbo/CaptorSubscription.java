package home.iot.db.dbo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptorSubscription {

	private int id;
	private int captorId;
	private Captor captor;
	private String host;
	private String hostname;
	private Date updated;
	private boolean enable;
}
