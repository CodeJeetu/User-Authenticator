
package com.user.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.user.auth.filters.JwtAuthenticationEntryPoint;
import com.user.auth.filters.JwtAuthenticationFilter;

/**
 * Configuring the security 
 * @author Jitendra 
 */
@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	/**
	 *  Configures security filters and authorization rules
	 * 
	 */
	@SuppressWarnings({ "deprecation", "deprecation", "removal" })
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		  
		http.csrf(csrf -> csrf.disable())
          .authorizeHttpRequests().
          requestMatchers("/user/**").authenticated() // Require authentication for /user/** endpoints
          .requestMatchers("/auth/**").permitAll()	// Allow unrestricted access to /auth/** endpoints
          .anyRequest() // // Require authentication for all other requests
          .authenticated()
          .and().exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
  http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // add Add JWT authentication filter
  return http.build();

	}
	
	/**
	 * This Bean is used to password encoding
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return  new BCryptPasswordEncoder();
	}
	
	/**
	 *  Retrieves the AuthenticationManager from AuthenticationConfiguration
	 * @param builder
	 * @return getAuthenticationManager
	 * 
	 */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

}
