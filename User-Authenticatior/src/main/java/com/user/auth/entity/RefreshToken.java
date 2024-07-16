
package com.user.auth.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is RefreshToken to store the Refresh token 
 */
@Entity
@Table(name = "auth_token_m")
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

	// This class is used to store the Refresh Token properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tokenid;
	
	private String refreshTocken;
	
	private Instant expiry ;

	@OneToOne
	private UserEntity user;
	
	
}
