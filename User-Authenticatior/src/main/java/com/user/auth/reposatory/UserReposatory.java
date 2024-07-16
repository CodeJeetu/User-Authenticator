
package com.user.auth.reposatory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.user.auth.entity.UserEntity;

/**
 * It is responsible to interact the UserDetails to Database
 */
@Repository
public interface UserReposatory extends JpaRepository<UserEntity, Integer> {

	/**
	 * Retrieve user details using email/username
	 * @param email
	 * @return Optional<UserEntity>
	 */
	Optional<UserEntity> findByEmail(String email);
	

	/**
	 * Checking if user is Exists or not 
	 * @param email
	 * @return Boolean
	 */
	boolean existsByEmail(String email);

	/**
	 * it is use to delete any user
	 * @param email
	 */ 
	void deleteByEmail(String email);

}
