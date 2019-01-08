package com.citrusbyte.poc.smartAc.web.exception;

public class DeviceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -9131452369747591275L;

	public DeviceNotFoundException() {
        super();
    }

    public DeviceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DeviceNotFoundException(final String message) {
        super(message);
    }

    public DeviceNotFoundException(final Throwable cause) {
        super(cause);
    }
}
