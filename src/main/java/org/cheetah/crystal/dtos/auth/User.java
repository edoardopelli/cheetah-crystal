package org.cheetah.crystal.dtos.auth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
@Document(collection = "users")
@JsonInclude(value = Include.NON_EMPTY)
@JsonIgnoreProperties(value = {"accountNonExpired","accountNonLocked","credentialsNonExpired","enabled"})
public class User implements UserDetails{
    private static final long serialVersionUID = -552655648862641502L;
	@Id
    private String id;
    private String username;
   
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    private String email;
    private String pin;
    private Boolean pinConfirmed = false;
    private String otp;
    private List<? extends GrantedAuthority> authorities = List.of();
    private Map<String,Object> metadata;
    
    //Authenticator part
    @JsonProperty(access = Access.WRITE_ONLY)
    private String secretKey;
    @JsonProperty(access = Access.WRITE_ONLY)
    private int validationCode;
    private List<Integer> scratchCodes;
    
    public List<? extends GrantedAuthority> getAuthorities(){
    	return authorities;
    }
	
   
}