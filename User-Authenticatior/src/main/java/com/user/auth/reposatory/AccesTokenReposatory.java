
package com.user.auth.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.auth.constants.AccessTokenStatus;
import com.user.auth.entity.AccesTokenEntity;

/**
 * It is responsible to interact the AccesTokenReposatory to Database
 */
public interface AccesTokenReposatory extends JpaRepository<AccesTokenEntity, Integer> {
	/**
	 * It checks if AccesToken is present with status
	 * @param String
	 * @param AccessTokenStatus
	 * @return Boolean
	 */
	boolean existsByAccestokenAndStatus(String accesToken, AccessTokenStatus status);
	
	/**
	 * To retrieve the AccesToken details using Accesstoken
	 * @param String
	 * @return AccesTokenEntity
	 */
	AccesTokenEntity findByAccestoken(String accessToken);
}
