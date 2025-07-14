package home.iot.ha;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SensorState {

	@JsonProperty("entity_id")
	private String entityId;

	@JsonProperty("state")
	private String state;

	@JsonProperty("attributes")
	private SensorAttributes attributes;

}