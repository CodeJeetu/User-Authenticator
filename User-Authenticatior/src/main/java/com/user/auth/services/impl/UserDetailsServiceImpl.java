
package com.user.auth.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.user.auth.config.SecurityConfig;
import com.user.auth.entity.UserEntity;
import com.user.auth.reposatory.UserReposatory;

import jakarta.transaction.Transactional;

/**
 * Implementation of UserDetailsService class 
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	 
	@Autowired
	UserReposatory reposatory;
	
	@Autowired
	SecurityConfig config;

	
	@Override
	@Transactional
	/**
	 * fetching user form database and storing in UserDetailsService 
	 */
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Retrieve user entity from repository based on username (email)    
		Optional<UserEntity> userOptional = reposatory.findByEmail(username);
	        UserEntity user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
	        // Build and return a UserDetails object representing the authenticated user
	        return org.springframework.security.core.userdetails.User.builder()
	                .username(user.getEmail())
	                .password(config.passwordEncoder().encode(user.getPassword()))
	                .roles("Admin")
	                .build();
	    }

}
