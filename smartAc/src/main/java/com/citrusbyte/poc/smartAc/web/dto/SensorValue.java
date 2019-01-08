package com.citrusbyte.poc.smartAc.web.dto;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class SensorValue {
	private String sensor_type;
	private String sensor_value;
	private String timestamp;
	
	public SensorValue() {
	}
	
	public SensorValue(String sensor_type, String sensor_value, String timestamp) {
		this.sensor_type = sensor_type;
		this.sensor_value = sensor_value;
		this.timestamp = timestamp;
	}
	
	public String getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}
	public String getSensor_value() {
		return sensor_value;
	}
	public void setSensor_value(String sensor_value) {
		this.sensor_value = sensor_value;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Date getTimestampAsDate() {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
	    TemporalAccessor accessor = timeFormatter.parse(timestamp);
	    return Date.from(Instant.from(accessor));
	}
}