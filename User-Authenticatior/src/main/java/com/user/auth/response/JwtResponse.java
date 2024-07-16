
package com.user.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response class which return all tokens and user
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {

	//This is designed for containing the tokens and user;
	private String accesToken;
	private String refreshToken;
	private String userName;
}
