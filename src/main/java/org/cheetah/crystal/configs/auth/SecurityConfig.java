package org.cheetah.crystal.configs.auth;

import org.cheetah.crystal.core.googleauth.GoogleAuthRepository;
import org.cheetah.crystal.core.servlet.filters.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.warrenstrange.googleauth.GoogleAuthenticator;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private TokenAuthenticationFilter tokenAuthenticationFilter;

	@Autowired
	private GoogleAuthRepository credentialRepository;

	@Bean
	GoogleAuthenticator gAuth() {
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		googleAuthenticator.setCredentialRepository(credentialRepository);
		return googleAuthenticator;
	}

	@Bean
	@Profile({ "local", "test" })
	SecurityFilterChain securityFilterChainNoAuth(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> authorize
				.requestMatchers( "/register/**", "/actuator", "/auth/login", "/auth/login/**",
					 "/confirm-pin", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
				.permitAll().anyRequest().authenticated())
				.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	@Profile({ "!local & !test" })
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/validate/key", "/register/**",  "/confirm-pin",
								"/auth/login", "/auth/login/**")
						.permitAll().requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").denyAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}