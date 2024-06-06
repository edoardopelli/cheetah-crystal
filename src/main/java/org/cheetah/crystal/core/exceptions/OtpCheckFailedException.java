package org.cheetah.crystal.core.exceptions;

public class OtpCheckFailedException extends AbstractCrystalException {

	public OtpCheckFailedException(String message) {
		super(message, "OTP_NOT_VALID", 400);
	}

}
