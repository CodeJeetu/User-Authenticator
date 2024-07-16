
package com.user.auth.filters;

import java.io.PrintWriter;



import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * It invokes when Authentication fails
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{


	/**
	 * This method is called by Spring Security when authentication fails due to
	 * unauthorized access attempts. It sets the HTTP status code to 401
	 * UNAUTHORIZED and sends a custom error message to the client.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws java.io.IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // defining error code 401 
		PrintWriter writer = response.getWriter();
		writer.println("Access Denied "+authException.getMessage());
		
	}
}
