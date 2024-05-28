package org.cheetah.crystal.core.exceptions;

public class PinNotFoundException extends AbstractCrystalException {
	private static final long serialVersionUID = 6656600395768694508L;

	public PinNotFoundException(String message) {
		super(message, "PIN_NOT_FOUND", 404);
	}
}