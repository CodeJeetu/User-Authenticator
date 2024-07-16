
package com.user.auth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.user.auth.constants.AccessTokenStatus;
import com.user.auth.entity.AccesTokenEntity;
import com.user.auth.reposatory.AccesTokenReposatory;
import com.user.auth.services.AccesTokenServices;

/**
 * This is Implementation of AccesTokenServices
 */
@Service
public class AccesTokenServicesImpl implements AccesTokenServices {
	
	@Autowired 
	AccesTokenReposatory reposatory;
	
	public ResponseEntity<?> saveAccessToken(AccesTokenEntity token){
		reposatory.save(token);
		return ResponseEntity.ok("saved");
	}
	
	/**
	 *  to check tocken is alive or not 
	 */
	public boolean isTokenRevoked(String token)
	{
		return reposatory.existsByAccestokenAndStatus(token,AccessTokenStatus.Revoked);
	}
	
	/**
	 * Implementation of revokeToken
	 */
	public ResponseEntity<?> revokeToken(String token)
	{
		AccesTokenEntity tokenEntity = reposatory.findByAccestoken(token);
		if(tokenEntity != null && !isTokenRevoked(token))
		{
			tokenEntity.setStatus(AccessTokenStatus.Revoked);
			reposatory.save(tokenEntity);
			return ResponseEntity.status(HttpStatus.OK).body("Token Revoked Sucessfully");
		}
		else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token and Not alive or Present");
		}
	}

}
