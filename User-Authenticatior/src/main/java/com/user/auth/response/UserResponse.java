
package com.user.auth.response;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

/**
 * Response class to reurn the user profile
 */
@Getter
@Setter
public class UserResponse {

	// This class is designed for containing the current user profile
	
	  private String username;
	  private String password;
	    private List<String> roles;
	    private boolean isActive;
	    
	    /**
	     * It is used to initialize the variables
	     * @param String
	     * @param String
	     * @param List<String>
	     * @param boolean
	     */
		public UserResponse(String username,String password, List<String> roles, boolean isActive) {
			this.username = username;
			this.password = password;
			this.roles = roles;
			this.isActive = isActive;	
		}
	    
	    
	    
}
