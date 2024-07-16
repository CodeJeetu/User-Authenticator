

package com.user.auth.entity;


import java.util.Date;

import org.hibernate.annotations.Collate;

import com.user.auth.constants.AccessTokenStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is AccesToken Entity to store the AccesToken details
 */
@Builder
@Data
@Getter
@Setter
@Entity
@Table(name = "auth_access_token_m")
@NoArgsConstructor
@AllArgsConstructor
public class AccesTokenEntity {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tokenId;
	
	private String accestoken;
	
	private Date expiry;
	
	private String user;
	
	@Enumerated(EnumType.STRING)
	private AccessTokenStatus status;
	
	
}
