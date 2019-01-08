package com.citrusbyte.poc.smartAc.web.exception;

public class DeviceIdMismatchException extends RuntimeException {
	private static final long serialVersionUID = -8966605512800634728L;

	public DeviceIdMismatchException() {
        super();
    }

    public DeviceIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DeviceIdMismatchException(final String message) {
        super(message);
    }

    public DeviceIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
