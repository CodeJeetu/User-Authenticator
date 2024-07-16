
package com.user.auth.reposatory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.user.auth.entity.RefreshToken;

import jakarta.transaction.Transactional;

/**
 * It is responsible to interact the RefreshToken to Database
 */
@Repository
public interface RefreshTockenReposatory  extends CrudRepository<RefreshToken, Integer>{

	/**
	 * Retrieve the Refresh token details using refresh token
	 * @param String
	 * @return Optional<RefreshToken>
	 */
	@Transactional
	Optional<RefreshToken> findByRefreshTocken(String refrestocken);

}
