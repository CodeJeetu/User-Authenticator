
package com.user.auth.services;

import java.util.List;

import com.user.auth.entity.UserEntity;

/**
 * It contains the methods for operating the User
 */
public interface UserService {
	
	/**
	 * To Save User
	 * @param user
	 */
	public void saveUser(UserEntity user);
	/**
	 * Fetch All user 
	 * @return  List<UserEntity>
	 */
	public List<UserEntity> fetchAllUser(); 
	
	/**
	 * Check if user Exists 
	 * @param String
	 * @return Boolean
	 */
	 public boolean isUserExists(String email) ;

}
