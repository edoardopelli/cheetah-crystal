package org.cheetah.crystal.core.exceptions;

import org.cheetah.crystal.rest.responses.ErrorResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;

@JsonIgnoreProperties(value = {"stackTrace","suppressed"})
@JsonInclude(value = Include.NON_NULL)
public abstract class AbstractCrystalException extends RuntimeException {

	private static final long serialVersionUID = -2404441168584606942L;

	@Getter
	private final ErrorResponse errorResponse;
	
	  public AbstractCrystalException(String message, String errorCode, int status) {
	        super(message);
	        this.errorResponse = new ErrorResponse(message, errorCode, status);
	    }
}
