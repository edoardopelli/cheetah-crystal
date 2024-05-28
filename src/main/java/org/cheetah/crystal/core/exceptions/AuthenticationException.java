package org.cheetah.crystal.core.exceptions;

public class AuthenticationException extends AbstractCrystalException {

	public AuthenticationException(String message) {
		super(message, "USERNAME_PASSWORD_WRONG", 401);
	}

	private static final long serialVersionUID = -204520355803336978L;

}
