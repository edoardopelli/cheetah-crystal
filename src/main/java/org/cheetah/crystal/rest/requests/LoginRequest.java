package org.cheetah.crystal.rest.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    
}
