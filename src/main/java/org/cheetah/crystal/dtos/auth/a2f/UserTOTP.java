package org.cheetah.crystal.dtos.auth.a2f;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTOTP {
	private String username;
	private String secretKey;
	private int validationCode;
	private List<Integer> scratchCodes;
}