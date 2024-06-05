package org.cheetah.crystal.core.googleauth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.cheetah.crystal.core.exceptions.UserNotFoundException;
import org.cheetah.crystal.core.utils.CrystalSessionManager;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.dtos.auth.UserTOTP;
import org.cheetah.crystal.mongodb.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warrenstrange.googleauth.ICredentialRepository;

@Component
public class GoogleAuthRepository implements ICredentialRepository {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public String getSecretKey(String userName) {
		String secretKey = getUser(userName).getSecretKey();
		System.out.println("get secretkey: "+secretKey);
		return secretKey;
	}

	@Override
	public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
		try {
			User user = CrystalSessionManager.getUser();
			System.out.println("put secretkey: "+secretKey);
			user.setSecretKey(secretKey);
			user.setValidationCode(validationCode);
			user.setScratchCodes(scratchCodes);
//			userRepository.save(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User getUser(String username) {
		Optional<User> optUser = userRepository.findByUsername(username);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		User user = optUser.get();

		return user;
	}

	
}
