package org.cheetah.crystal.rest.responses;

import org.cheetah.crystal.dtos.auth.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
public class UserResponse extends AbstractCrystalResponse{

	
	private User user;
}
