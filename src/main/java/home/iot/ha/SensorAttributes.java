package home.iot.ha;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SensorAttributes {
	@JsonProperty("friendly_name")
	private String friendlyName;

}