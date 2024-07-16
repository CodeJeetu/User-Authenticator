
package com.user.auth.filters;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.user.auth.response.AuthException;
import com.user.auth.services.impl.AccesTokenServicesImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtAuthenticationFilter is a Spring Framework filter that extends OncePerRequestFilter. It intercepts incoming HTTP requests,
 * extracts JWTs from Authorization headers, validates their integrity, expiration, and signature using a provided JwtTokenProvider, 
 * and sets up user authentication within the SecurityContextHolder if the token is deemed valid. This class is crucial for
 * implementing JWT-based authentication and authorization mechanisms in Spring Boot applications.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	 private org.slf4j.Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	    @Autowired
	    private UserDetailsService userDetailsService;
	    
	    @Autowired
	    private AccesTokenServicesImpl accesTokenServicesImpl;

	    @Autowired
	    private JwtHelper jwtHelper;

	    /**
	     * Filters incoming HTTP requests to validate JSON Web Tokens (JWTs) and establish user authentication.
	     * Extends OncePerRequestFilter to ensure this filter is applied exactly once per request.
	     */
	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException, java.io.IOException {

	        String requestURI = request.getRequestURI();
	        // Extract token from Authorization header
	        String requestHeader = request.getHeader("Authorization");
	        String username = null;
	        String token = null;

	        if (requestHeader != null) { // && requestHeader.startsWith("Bearer")) {
	            token = requestHeader; //.substring(7); Tried Bearer AUth
	            try { 
	                // Get username from token
	                username = this.jwtHelper.getUsernameFromToken(token);
	            } catch (IllegalArgumentException e) {
	                logger.info("Illegal Argument while fetching the username !!");
	                AuthException.raiseException(response, "Illegal Argument: " + e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
	                return;
	            } catch (ExpiredJwtException e) {
	                logger.info("Token Expired !!");
	                logger.info(e.getMessage());
	                AuthException.raiseException(response, "acces Token Expired "+e.getMessage().substring(11,35)+", Please Login Again   Thankyou", HttpServletResponse.SC_UNAUTHORIZED);
	                return;
	            } catch (MalformedJwtException e) {
	                logger.info("Invalid Token !!");
	                AuthException.raiseException(response, "Invalid Token: " + e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
	                return;
	            } catch (io.jsonwebtoken.security.SignatureException e) {
	                logger.info("Invalid Signature !!");
	                logger.info(e.getMessage());
	                AuthException.raiseException(response, "Invalid Signature: ", HttpServletResponse.SC_BAD_REQUEST);
	                return;
	            } catch (Exception e) {
	                logger.error("Unexpected error during JWT processing: ", e);
	                AuthException.raiseException(response, "Internal Server Error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                return;
	            }
	        } else {
	            logger.info("Ok You Are Going to login ....");
	        }

	        if (!requestURI.equals("/auth/login")) {
	            // For paths other than /login, proceed with token validation and authentication setup
	            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	                // Fetch user details from username
	                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
	                Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
	                if (validateToken) {
	                    // Set the authentication
	                if(!accesTokenServicesImpl.isTokenRevoked(token))
	                {
	                    		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	                    		userDetails, null, userDetails.getAuthorities());
	                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                    SecurityContextHolder.getContext().setAuthentication(authentication);
	                }else {
	                	logger.info("Token has been revoked !!");
	                    AuthException.raiseException(response, "Token is no more alive", HttpServletResponse.SC_GONE);
	                    return;
	                }
	                }else {
	                    logger.info("Validation fails !!");
	                    AuthException.raiseException(response, "Token Validation Failed", HttpServletResponse.SC_UNAUTHORIZED);
	                    return;
	                }
	            }
	        }
	        filterChain.doFilter(request, response);
	    }


}
