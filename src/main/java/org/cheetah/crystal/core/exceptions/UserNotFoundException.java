package org.cheetah.crystal.core.exceptions;

public class UserNotFoundException extends AbstractCrystalException {
	private static final long serialVersionUID = -2519834429030289397L;

	public UserNotFoundException(String message) {
		super(message, "USER_NOT_FOUND", 404);
	}
}