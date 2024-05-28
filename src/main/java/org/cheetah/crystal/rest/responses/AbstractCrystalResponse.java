package org.cheetah.crystal.rest.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;



@JsonInclude(value = Include.NON_NULL)
public abstract class AbstractCrystalResponse {

	@Getter
	@Setter
	private  Exception exception;

}
