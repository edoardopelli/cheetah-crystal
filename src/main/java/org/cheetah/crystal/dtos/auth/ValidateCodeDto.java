package org.cheetah.crystal.dtos.auth;

import lombok.Data;

@Data
public class ValidateCodeDto {
	
	private String username;
	private String code;
}