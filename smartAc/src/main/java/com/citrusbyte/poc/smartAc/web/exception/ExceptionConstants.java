package com.citrusbyte.poc.smartAc.web.exception;

public enum ExceptionConstants {
	JSON_PARSE_EXCEPTION("AC-0001"), JSON_MAPPING_EXCEPTION("AC-0002"), IO_EXCEPTION("AC-0003"), DEVICE_NOT_FOUND("AC-0004");
	
	private String code;

	private ExceptionConstants(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	
}
