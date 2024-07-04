package org.cheetah.crystal.controllers.auth.a2f.google;

import org.cheetah.crystal.core.services.auth.a2f.google.GoogleAuthenticationService;
import org.cheetah.crystal.dtos.auth.a2f.ValidateCodeDto;
import org.cheetah.crystal.rest.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/2fa/google")
public class GoogleAuthController {

	@Autowired
	private GoogleAuthenticationService service;

	/**
	 * 
	 * @param request
	 * @return Se l'autenticazione va a buon fine restituisce "OTP" <br>
	 * per indicare al client di indirizzare alla pagina per inserire l'OTP
	 */
	@PostMapping(path = "/login")
	public ResponseEntity<String> login2fa(@RequestBody LoginRequest request) {
		String result = service.twoFactorAuthenticate(request);
		return ResponseEntity.ok(result);
	}

	/**
	 * 
	 * @param dto
	 * @return token di autenticazione
	 */
	@PostMapping(path = "/otp")
	public ResponseEntity<String> otp(@RequestBody ValidateCodeDto dto) {
		String token = service.authorizeUser(dto.getUsername(), dto.getCode());
		return ResponseEntity.ok(token);
	}

}
