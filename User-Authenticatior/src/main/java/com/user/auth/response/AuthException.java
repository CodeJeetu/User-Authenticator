
package com.user.auth.response;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletResponse;

/**
 * This is a custom Exception Response class to print some known Exceptions
 */
public class AuthException extends RuntimeException{
	
	/**
	 * Some of Exception will be printed on output
	 * @param HttpServletResponse response
	 * @param String message
	 * @param Integer status
	 * @throws IOException
	 */
	public static void raiseException(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
    }

}
