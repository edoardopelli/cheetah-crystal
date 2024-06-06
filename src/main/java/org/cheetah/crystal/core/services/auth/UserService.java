package org.cheetah.crystal.core.services.auth;
import java.security.SecureRandom;
import java.util.Optional;

import org.cheetah.crystal.core.exceptions.PinNotFoundException;
import org.cheetah.crystal.core.exceptions.UserAlreadyExistsException;
import org.cheetah.crystal.core.exceptions.UserNotFoundException;
import org.cheetah.crystal.dtos.auth.Pin;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.mongodb.repositories.auth.PinRepository;
import org.cheetah.crystal.mongodb.repositories.auth.UserRepository;
import org.cheetah.crystal.redis.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private PinRepository pinRepository;

    @Autowired
	private UsernamePasswordAuthenticationService tokenService;

    @Autowired
    private RedisService redisService;


    public User registerNewUser(User user)   {
    	Optional<User> opt = userRepository.findByUsername(user.getUsername()); 
        if (opt.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            generatePin(user);
            return user;
        }
        throw new UserAlreadyExistsException("Username already exists");
    }
    
    public User confirmPin(String pin) {
        Optional<Pin> pinOptional = pinRepository.findByPin(pin);
        if (!pinOptional.isPresent()) {
            throw new PinNotFoundException("PIN not found");
        }

        Pin pinObject = pinOptional.get();
        Optional<User> userOptional = userRepository.findById(pinObject.getUserId());
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();
        user.setPinConfirmed(true);
        user.setPin(null); // Cancella il valore del pin nello User
        userRepository.save(user);

        pinRepository.delete(pinObject); // Cancella il PIN dalla collezione pins

        return user;
    }
    
    private void generatePin(User user) {
        String pin;
        do {
            pin = ServicesUtil.generatePin();
        } while (pinRepository.findByPin(pin).isPresent());

        Pin pinObject = new Pin(pin, user.getId());
        user.setPin(pin);
        pinRepository.save(pinObject);
    }

}