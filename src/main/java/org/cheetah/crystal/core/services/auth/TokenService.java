package org.cheetah.crystal.core.services.auth;

import java.util.Optional;
import java.util.UUID;

import org.cheetah.crystal.core.exceptions.ExpiredOTPException;
import org.cheetah.crystal.core.exceptions.InvalidOTPException;
import org.cheetah.crystal.core.exceptions.PasswordWrongException;
import org.cheetah.crystal.core.exceptions.PinNotConfirmedException;
import org.cheetah.crystal.core.exceptions.UserNotFoundException;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.mongodb.repositories.auth.UserRepository;
import org.cheetah.crystal.redis.services.RedisService;
import org.cheetah.crystal.rest.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisService redisService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String generateToken(User user) {
		// Genera un token di autenticazione (puoi utilizzare JWT o qualsiasi altro
		// metodo)
		return UUID.randomUUID().toString(); // Semplice token UUID per esempio
	}

	public String authenticate(LoginRequest loginRequest) {
		User user = getUser(loginRequest);
		String token = generateToken(user);
		redisService.saveUserToken(token, user);
		return token;

	}

	public User getOtp(LoginRequest loginRequest) {
		User user = getUser(loginRequest);

		String otp;
		do {
			otp = ServicesUtil.generatePin();
		} while (userRepository.findByOtp(otp).isPresent());

		user.setOtp(otp);
		redisService.saveUserOtp(otp, user);
		return user;
	}
	
	public String findUserByIdAndOtp(String userId, String otp) {
		Optional<User> userOptional = userRepository.findById(userId);
		if(userOptional.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		final String otpFound = redisService.getUserOtp(userId);
		if(otpFound==null) {
			throw new ExpiredOTPException("Otp expired");
		}
		if(otp!=null && otp.equals(otpFound)) {
			String token = generateToken(new User());
			redisService.saveUserToken(token, userOptional.get());
			return token;
		}
		
		throw new InvalidOTPException("The Otp is Invalid");
	}

	private User getUser(LoginRequest loginRequest) {
		Optional<User> opt = userRepository.findByUsername(loginRequest.getUsername());
		if (opt.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		User user = opt.get();
		if(!user.getPinConfirmed()) {
			throw new PinNotConfirmedException("Pin not confirmed");
		}
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new PasswordWrongException("The password is wrong");
		}
		return user;
	}

	

}