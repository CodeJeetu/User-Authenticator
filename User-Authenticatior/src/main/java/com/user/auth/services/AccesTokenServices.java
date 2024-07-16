
package com.user.auth.services;

import org.springframework.http.ResponseEntity;

import com.user.auth.entity.AccesTokenEntity;

/**
 * It contains the methods for operating the AccessTokens
 */
public interface AccesTokenServices {

	/**
	 * To save the Access Token into Database
	 * @param AccesTokenEntity
	 * @return ResponseEntity
	 */
	public ResponseEntity<?> saveAccessToken(AccesTokenEntity token);
	/**
	 * To check is Access Token Revoked/killed or Not
	 * @param String
	 * @return Boolean
	 */
	public boolean isTokenRevoked(String token);
	/**
	 * To Revoke/kill the Access Token
	 * @param String
	 * @return ResponseEntity
	 */
	public ResponseEntity<?> revokeToken(String token);
}
