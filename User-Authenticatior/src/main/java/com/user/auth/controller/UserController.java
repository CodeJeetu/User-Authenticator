
package com.user.auth.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.auth.entity.UserEntity;
import com.user.auth.request.AccesTokenrequest;
import com.user.auth.request.UserRequest;
import com.user.auth.response.UserResponse;
import com.user.auth.services.impl.AccesTokenServicesImpl;
import com.user.auth.services.impl.UserServiceImpl;


/**
 * This is the protected endpoint to access this Authentication is required 
 * @author Jitendra
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserServiceImpl service;
	
	@Autowired 
	AccesTokenServicesImpl accesTokenServicesImpl;
	
	@GetMapping("/")
	public ResponseEntity<?> test()
	{
		return ResponseEntity.ok("User Api is Runing...");
	}
	/**
	 * User Can also create another User
	 * @param user
	 * @return
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserEntity user)
	{
		service.saveUser(user);
		return ResponseEntity.ok("User created Sucessfully");
	}
	
	/**
	 * to fetch all users
	 * @return List of users
	 */
	@GetMapping("/all")
	public List<UserEntity>getAllUser()
	{
		return service.fetchAllUser();
	}
	/**
	 * delete a user by email
	 * @param request
	 * @return the httpstatus 
	 */
	@PostMapping("/remove")
	public ResponseEntity<?> deleteUser(@RequestBody UserRequest request)
	{
		return service.deleteUser(request.getEmail());
	}
	
	/**
	 * fetch current user and their status
	 * @return
	 */
	@GetMapping("/profile")
    public UserResponse userProfile() {
		 // Retrieve authentication details from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            boolean isActive = userDetails.isEnabled(); // Assuming isEnabled() represents active status
        	String password = userDetails.getPassword();
            if(password!= null)
            	 password = "*********"; // password is masked
            boolean accountNonExpired = userDetails.isAccountNonExpired();
            boolean accountNonLocked = userDetails.isAccountNonLocked();
            boolean credentialsNonExpired = userDetails.isCredentialsNonExpired();

            return new UserResponse(username,password, roles, isActive);
        } else {
        	 new RuntimeException("Authentication  Required ..!"); 
            return null;
        }
    }
	/**
	 *  This endpoint is used to revoke/kill an accessToken 
	 * @param accessToken
	 * @return HttpStatus
	 */
	@PostMapping("/revoke")
	 ResponseEntity<?> killAccessToken(@RequestBody AccesTokenrequest accessToken)
     {
     	return accesTokenServicesImpl.revokeToken(accessToken.getAccessToken()); 
     }
	
	
}
