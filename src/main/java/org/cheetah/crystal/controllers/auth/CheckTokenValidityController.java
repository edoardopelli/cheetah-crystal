package org.cheetah.crystal.controllers.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.redis.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth/check")
public class CheckTokenValidityController {

	@Autowired
	private RedisService redisService;

	@PostMapping("/token")
	public ResponseEntity<User> postMethodName(@RequestBody String token) {
		User user = redisService.getUserByToken(token);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Crystal-Error", "Token expired or not valid")
					.build();
		}
		return ResponseEntity.ok(user);
	}

}
