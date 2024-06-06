package org.cheetah.crystal.controllers.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrenstrange.googleauth.GoogleAuthenticator;

import org.cheetah.crystal.core.services.auth.TwoFactorAuthenticationService;
import org.cheetah.crystal.dtos.auth.ValidateCodeDto;
import org.cheetah.crystal.rest.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth/2fa")
public class TwoFactorAuthController {
	
	@Autowired
	private TwoFactorAuthenticationService service;
	

	@PostMapping(path = "/login")
	public ResponseEntity<String> login2fa(@RequestBody LoginRequest request) {
		String result = service.twoFactorAuthenticate(request);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(path = "/otp") 
	public ResponseEntity<String> otp(@RequestBody ValidateCodeDto dto){
		String token =service.authorizeUser(dto.getUsername(),dto.getCode());
		return ResponseEntity.ok(token);
	}
	
}
