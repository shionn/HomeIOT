package home.iot;

import java.util.regex.Pattern;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Captor {
	private int id;
	private String command;
	private Pattern sensor;
}
