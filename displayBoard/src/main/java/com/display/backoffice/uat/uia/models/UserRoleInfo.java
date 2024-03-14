package com.display.backoffice.uat.uia.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleInfo {

	private String userRoleSeq;
	private String roleId;
	private String userId;
	private String lastUpdtPnttm;
	private String lastUpdusrId;
}
