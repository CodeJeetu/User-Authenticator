
package com.user.auth.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.auth.entity.UserEntity;
import com.user.auth.reposatory.UserReposatory;
import com.user.auth.services.UserService;

import jakarta.transaction.Transactional;

/**
 * Implementation of UserService
 */
@Service
public class UserServiceImpl  implements UserService {
	
	@Autowired
	private UserReposatory reposatory;
	
	public void saveUser(UserEntity user)
	{
		reposatory.save(user);
	}
	
	/**
	 * Implementing userExists check point
	 */
	 public boolean isUserExists(String email) {
		 return reposatory.existsByEmail(email);
	    }
	
	 /**
	  * it is used to  fetch all user list
	  */
	public List<UserEntity> fetchAllUser()
	{
		return reposatory.findAll();
	}
	
	/**
	 * it is used to delete an user 
	 * @param email
	 * @return ResponseEntity
	 */
	@Transactional
	public ResponseEntity<?> deleteUser(String email){
	reposatory.deleteByEmail(email);
	return ResponseEntity.status(HttpStatus.OK.value()).body("User "+email+" has been deleted sucessfully");
	}

}
