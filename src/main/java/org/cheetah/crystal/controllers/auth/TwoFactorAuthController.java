package org.cheetah.crystal.controllers.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.cheetah.crystal.rest.requests.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth/2fa")
public class TwoFactorAuthController {

	@PostMapping(path = "/login")
	public ResponseEntity<String> postMethodName(@RequestBody LoginRequest request) {
		
		return entity;
	}
	
}
