package org.cheetah.crystal.controllers.auth;


import org.cheetah.crystal.core.services.auth.TokenService;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.rest.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = tokenService.authenticate(loginRequest);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/login/otp/request")
    public ResponseEntity<User> loginRequestOtp(@RequestBody LoginRequest loginRequest) {
    	User user = tokenService.getOtp(loginRequest);
    	return ResponseEntity.ok(user);
    }
    
    @PostMapping("/login/otp/{userId}")
    public ResponseEntity<String>  loginWithOtp(@PathVariable("userId") String userId,@RequestHeader(value = "X-Crystal-OTP") String otp){
    	String token = tokenService.findUserByIdAndOtp(userId,otp);
    	return ResponseEntity.ok(token);
    }
    
    
}