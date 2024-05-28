package org.cheetah.crystal.core.exceptions;

public class PasswordWrongException extends AbstractCrystalException {

	public PasswordWrongException(String message) {
		super(message, "WRONG_PASSWORD", 401);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -6017078127161675897L;

}
