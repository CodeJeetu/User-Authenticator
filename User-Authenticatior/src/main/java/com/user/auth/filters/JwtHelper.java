
package com.user.auth.filters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.user.auth.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * This class is dedicated for jwt token, it handles all operation of access token.
 * 
 */
@Component
public class JwtHelper {
	
	  
	@Value("${app.jwt.tokenValidityInSeconds}")
		private  long jwtAccessTokenExpiry;  

		
	    /**
	     * secret key used to encrypt and decrypt the token
	     */
	    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

	    /**
	     * Retrieving user from AccessToken
	     * @param token
	     * @return String
	     */
	    public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }

	    /**
	     * Retrieving expiration date from awt token
	     * @param token
	     * @return Date
	     */
	    public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }

	    /**
	     * Retrieving all claims from jwt token
	     * @param 
	     * @param String
	     * @param claimsResolver
	     * @return T
	     */
	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }

	    /**
	     *Retrieving any information from token we will need the secret key
	     * @param String
	     * @return Claims
	     */
	    @SuppressWarnings("deprecation")
		private Claims getAllClaimsFromToken(String token) {
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }

	    /**
	     * Check if the token has expired
	     * @param String
	     * @return Boolean
	     */
	    private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new java.util.Date());
	    }

	    /**
	     * Generate token for user
	     * @param String
	     * @return String
	     */
	    public String generateToken(String username) {
	        Map<String, Object> claims = new HashMap<>();
	        return doGenerateToken(claims, username);
	    }

	    /**
	     * While creating the token:
	     * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	     * 2. Sign the JWT using the HS512 algorithm and secret key.
	     * @param Map<String,Object>
	     * @param String
	     * @return String
	     */
	    private String doGenerateToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + jwtAccessTokenExpiry * 1000))
	                .signWith(SignatureAlgorithm.HS512, secret).compact();
	    }

	    /**
	     * It is used to validate the given token it match token user and stored user 
	     * @param String
	     * @param userDetails
	     * @return Boolean
	     */
	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = getUsernameFromToken(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

}
