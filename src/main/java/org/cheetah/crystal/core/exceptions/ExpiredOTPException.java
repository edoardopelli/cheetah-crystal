package org.cheetah.crystal.core.exceptions;

public class ExpiredOTPException extends AbstractCrystalException {

	public ExpiredOTPException(String message) {
		super(message, "EXPIRED_OTP", 400);
	}

	private static final long serialVersionUID = 467201583115544815L;

}
