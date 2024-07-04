package org.cheetah.crystal.dtos.auth.a2f;

import lombok.Data;

@Data
public class ValidateCodeDto {
	
	private String username;
	private String code;
}