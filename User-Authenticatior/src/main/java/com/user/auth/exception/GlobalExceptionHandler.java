
package com.user.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * It Centralized Exception and prints
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * Some well known exception are captured 
	 * @param ex
	 * @return
	 */
	 @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
	        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(); // Default to 500
	        String errorMessage = "Internal Server Error"; // Default message

	        // handling some known Exceptions
	        if (ex.getMessage().contains("is also Expired")) {
	            statusCode = HttpStatus.UNAUTHORIZED.value(); // 401
	            errorMessage = "Refresh Token is also expired, Please login Again";
	        } else if (ex.getMessage().contains("Tocken Not Found")) {
	            statusCode = HttpStatus.NOT_FOUND.value(); // 404
	            errorMessage = "Refresh Token not found";
	        }
	        return ResponseEntity.status(statusCode).body(errorMessage);
	    }

}
