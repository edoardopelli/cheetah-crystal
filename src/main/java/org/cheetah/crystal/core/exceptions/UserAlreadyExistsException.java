package org.cheetah.crystal.core.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends AbstractCrystalException {
	private static final long serialVersionUID = -258832182332030519L;

	public UserAlreadyExistsException(String message) {
		super(message, "USER_ALREADY_EXISTS", HttpStatus.BAD_REQUEST.value());
	}
}