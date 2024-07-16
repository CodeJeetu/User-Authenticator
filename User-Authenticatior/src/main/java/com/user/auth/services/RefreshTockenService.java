
package com.user.auth.services;

import java.io.IOException;

import com.user.auth.entity.RefreshToken;

/**
 * It contains the methods for operating the Refresh Token
 */
public interface RefreshTockenService {
	
	/**
	 * Verify the Refresh token 
	 * @param String
	 * @return RefreshToken
	 * @throws IOException 
	 */
	public RefreshToken verifyRefreshTocken(String refrestocken) throws IOException;
	/**
	 * It is used to create A Refresh Token using user/email
	 * @param String
	 * @return RefreshToken
	 */
	RefreshToken createRefreshToken(String username);

}
