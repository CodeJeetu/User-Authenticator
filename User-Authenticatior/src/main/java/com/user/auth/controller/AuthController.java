/**
 * It Controls the Authentication process
 */
package com.user.auth.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.auth.constants.AccessTokenStatus;
import com.user.auth.entity.AccesTokenEntity;
import com.user.auth.entity.RefreshToken;
import com.user.auth.entity.UserEntity;
import com.user.auth.filters.JwtHelper;
import com.user.auth.request.JwtRequest;
import com.user.auth.request.RefreshTokenRequest;
import com.user.auth.response.AuthException;
import com.user.auth.response.JwtResponse;
import com.user.auth.services.UserService;
import com.user.auth.services.impl.AccesTokenServicesImpl;
import com.user.auth.services.impl.RefreshTockenServiceImpl;

/**
 * This Controllers Handles all operations related to Authentication all end-points are public
 * @author Jitendra kushwaha
 * 
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;
	
    
    @Autowired
    private JwtHelper helper;
    
    @Autowired
    private UserService userService ;
  
    @Autowired
    private RefreshTockenServiceImpl refreshtokenservice;
    
    @Autowired
    AccesTokenServicesImpl accesTokenServicesImpl;
  
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    /**
     * test the /auth  is started or not
     * @return
     */
    @GetMapping("/")
    public  ResponseEntity<?> test()
    {
    
    	return ResponseEntity.ok("Auth Api is running ...");
    }
    
    /**
     * To create new user it will check first user is unique or not 
     * @param user request or Entity class
     * @return
     */
    @PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserEntity user)
	{
    	if (!userService.isUserExists(user.getEmail())) {
            userService.saveUser(user);
            return ResponseEntity.ok("User created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body("User with the same email is already available");
        }
	}
    
    
    /**
     * login and generate all tokens
     * @param request
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(request.getEmail()); // To generate The Tockens
        // call service to save the token 
        AccesTokenEntity accesToken = AccesTokenEntity.builder()
        		.accestoken(token)
        		.expiry(this.helper.getExpirationDateFromToken(token))
        		.user(userDetails.getUsername())
        		.status(AccessTokenStatus.Active)
        		.build();
        ResponseEntity<?> tokenSaved = this.accesTokenServicesImpl.saveAccessToken(accesToken);
        		
        /**
         * generating refresh token at initial time
         */
        RefreshToken refreshTocken = this.refreshtokenservice.createRefreshToken(userDetails.getUsername());
        JwtResponse response = JwtResponse.builder()
        		.accesToken(token)
        		.refreshToken(refreshTocken.getRefreshTocken())
        		.userName(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
  /**
   * Authenticate the user using stored user and password 
   * @param email
   * @param password
   */
    public void doAuthenticate(String email, String password) {
    	  // Create an authentication token with the provided email and password
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
        	  /**
        	   *  Authenticate the user using the AuthenticationManager
        	   */
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    
    /**
     *  To refresh/renew the access token using a initial generated refresh token
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) throws IOException {
        try {
        	  /**
        	   *  Verify the refresh token provided in the request
        	   */
    	RefreshToken refreshToken = refreshtokenservice.verifyRefreshTocken(request.getRefreshToken());
    	 
    	/**
    	 *  Retrieve the user associated with the refresh token
    	 */
        UserEntity userEntity = refreshToken.getUser();
        /**
         *  Generate a new access token using email
         */
        String token = this.helper.generateToken(userEntity.getEmail());
        
        return  ResponseEntity.ok(JwtResponse.builder()
                .refreshToken(refreshToken.getRefreshTocken())
                .accesToken(token)
                .userName(userEntity.getEmail())
                .build()
                );
        }
        catch (AuthException e) {
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
    }

       
}
