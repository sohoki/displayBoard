package com.display.backoffice.uat.uia.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {

	private String userId;
	
	private String refreshToken;
	
	private String expireat;
	
	private String ipAddress;
	
	private String userName;
}
