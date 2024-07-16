
package com.user.auth.services.impl;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.user.auth.entity.RefreshToken;
import com.user.auth.entity.UserEntity;
import com.user.auth.reposatory.RefreshTockenReposatory;
import com.user.auth.reposatory.UserReposatory;
import com.user.auth.response.AuthException;
import com.user.auth.services.RefreshTockenService;


/**
 * Implementation of RefreshTockenService 
 */
@Service
public class RefreshTockenServiceImpl implements RefreshTockenService {
	
	// this service is used to perform the Token implementation
	@Autowired
	RefreshTockenReposatory tokcenReposatory;
	
	@Autowired
	UserReposatory userReposatory;
	
	@Value("${app.refresh.tokenValidityInSeconds}")
	private  long expiryTimeForRefreshTocken;
	
	 
	@Override
	/**
	  *  Retrieve user entity from database based on username/email
	  */
	public RefreshToken createRefreshToken(String username) {
	    // Get existing refresh token for the user, if any
	    UserEntity user = userReposatory.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
	    RefreshToken refreshToken = user.getRefreshTocken();

	    // Calculate expiry time (1 minute from now)
	    Instant expiryTime = Instant.now().plusSeconds(expiryTimeForRefreshTocken); // 60 seconds = 1 minute

	    if (refreshToken == null) {
	        // If no existing refresh token found, create a new one
	        refreshToken = RefreshToken.builder()
	                .refreshTocken(UUID.randomUUID().toString())
	                .expiry(expiryTime)
	                .user(user)
	                .build();
	    } else {
	        // Update the expiry time of the existing refresh token
	        refreshToken.setExpiry(expiryTime);
	    }

	    user.setRefreshTocken(refreshToken); // Update user entity with the new or updated refresh token
	    tokcenReposatory.save(refreshToken); // Save the refresh token in the database

	    return refreshToken;
	}
	
    @Override
    /**
	 * Verify the RefreshToken is valid or not
	 */
	public RefreshToken verifyRefreshTocken(String refrestocken) throws IOException
	{
		 // Retrieve refresh token from repository based on token string
		RefreshToken token = tokcenReposatory.findByRefreshTocken(refrestocken).
				orElseThrow(()-> new RuntimeException("Refresh Tocken Not Found ..!"));
		// Check if refresh token has expired
		if(token.getExpiry().compareTo(Instant.now())<0)
		{
			// If expired, delete the token from repository
			tokcenReposatory.delete(token);
			throw new RuntimeException("Refresh Token is also Expired");
		}
		return token;
	}
	
}

