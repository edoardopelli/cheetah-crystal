package org.cheetah.crystal.core.exceptions;

public class InvalidOTPException extends AbstractCrystalException {

	public InvalidOTPException(String message) {
		super(message, "INVALID_OTP", 400);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -7991247920877324268L;

}
