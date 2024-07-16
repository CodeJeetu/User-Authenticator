
package com.user.auth.request;

import org.springframework.web.client.HttpClientErrorException.Forbidden;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class for login/Token Generation
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtRequest {
	
	// This class is for containing the login properties/ request to generate access token s
	private String email;
	private String password;

}
