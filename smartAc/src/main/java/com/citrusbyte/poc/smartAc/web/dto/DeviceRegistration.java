package com.citrusbyte.poc.smartAc.web.dto;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class DeviceRegistration {
	private String device_serial_number;
	private String firmware_version;
	private String timestamp;
	
	public String getDevice_serial_number() {
		return device_serial_number;
	}
	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}
	public String getFirmware_version() {
		return firmware_version;
	}
	public void setFirmware_version(String firmware_version) {
		this.firmware_version = firmware_version;
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
