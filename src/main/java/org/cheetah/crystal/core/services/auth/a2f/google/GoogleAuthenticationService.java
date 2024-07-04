package org.cheetah.crystal.core.services.auth.a2f.google;

import java.util.Optional;

import org.cheetah.crystal.core.exceptions.OtpCheckFailedException;
import org.cheetah.crystal.core.services.auth.AbstractAuthenticationService;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.mongodb.repositories.auth.UserRepository;
import org.cheetah.crystal.redis.services.RedisService;
import org.cheetah.crystal.rest.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.warrenstrange.googleauth.GoogleAuthenticator;

@Service
public class GoogleAuthenticationService extends AbstractAuthenticationService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private GoogleAuthenticator gAuth;


	@Override
	public UserRepository getRepository() {
		return userRepository;
	}
	
	public String twoFactorAuthenticate(LoginRequest loginRequest) {
		getUser(loginRequest);
		return "OTP";
	}

	public String authorizeUser(String username, String code) {
		boolean authorize =  gAuth.authorizeUser(username, Integer.parseInt(code));
		if(!authorize) {
			throw new OtpCheckFailedException("Otp is not valid");
		}
		Optional<User> optUser = getRepository().findByUsername(username);
		if(optUser.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		User user = optUser.get();
		String token = generateToken(user);
		redisService.saveUserToken(token, user);
		return token;
	}


}
