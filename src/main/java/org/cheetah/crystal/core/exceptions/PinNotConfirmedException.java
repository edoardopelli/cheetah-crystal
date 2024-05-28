package org.cheetah.crystal.core.exceptions;

public class PinNotConfirmedException extends AbstractCrystalException {

	private static final long serialVersionUID = -3118233281249491447L;

	public PinNotConfirmedException(String message) {
		super(message, "PIN_NOT_CONFIRMED", 400);
		// TODO Auto-generated constructor stub
	}

}
