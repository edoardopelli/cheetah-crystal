package org.cheetah.crystal.controllers.auth;


import org.cheetah.crystal.core.services.auth.TokenService;
import org.cheetah.crystal.rest.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/basic")
public class UsernamePasswordLoginController {

    @Autowired
    private TokenService tokenService;
    

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = tokenService.authenticate(loginRequest);
        return ResponseEntity.ok(token);
    }
    

    
    
}