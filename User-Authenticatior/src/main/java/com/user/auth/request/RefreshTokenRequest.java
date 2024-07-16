
package com.user.auth.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for RefreshToken endpoint
 */
@Data
@Getter
@Setter
public class RefreshTokenRequest {
	// this is request class to refresh the access-token using refresh-token
	private String refreshToken;

}
