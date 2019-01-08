package com.citrusbyte.poc.smartAc.web.dto;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

public class SensorDataDto {
	private String device_serial_number;
	private List<SensorValue> sensor_values;
	private String device_health_status;
	private String timestamp;
	
	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public List<SensorValue> getSensor_values() {
		return sensor_values;
	}

	public void setSensor_values(List<SensorValue> sensor_values) {
		this.sensor_values = sensor_values;
	}

	public String getDevice_health_status() {
		return device_health_status;
	}

	public void setDevice_health_status(String device_health_status) {
		this.device_health_status = device_health_status;
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
