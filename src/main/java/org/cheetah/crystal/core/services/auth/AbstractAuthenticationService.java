package org.cheetah.crystal.core.services.auth;

import java.util.Optional;
import java.util.UUID;

import org.cheetah.crystal.core.exceptions.PasswordWrongException;
import org.cheetah.crystal.core.exceptions.UserNotFoundException;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.mongodb.repositories.auth.UserRepository;
import org.cheetah.crystal.rest.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AbstractAuthenticationService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public abstract UserRepository getRepository();
	
	public String generateToken(User user) {
		// Genera un token di autenticazione (puoi utilizzare JWT o qualsiasi altro
		// metodo)
		return UUID.randomUUID().toString(); // Semplice token UUID per esempio
	}
		
	public User getUser(LoginRequest loginRequest) {
		Optional<User> opt = getRepository().findByUsername(loginRequest.getUsername());
		if (opt.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		User user = opt.get();
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new PasswordWrongException("The password is wrong");
		}
		return user;
	}
}
