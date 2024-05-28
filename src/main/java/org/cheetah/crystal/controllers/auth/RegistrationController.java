package org.cheetah.crystal.controllers.auth;

import org.cheetah.crystal.core.services.auth.UserService;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.rest.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> registerUser(@RequestBody User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setUser(user);
		user = userService.registerNewUser(user);
		user.setPassword(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
	}
}