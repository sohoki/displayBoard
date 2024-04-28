package com.display.backoffice.uat.role.models.dto;


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
public class RoleInfoVO {

	private String roleId;
	private String roleName;
	private String roleDc;
	private String roleUseyn;
	private String systemCode;
	private String frstRegistPnttm;
	private String frstRegisterId;
	private String lastUpdtPnttm;
	private String lastUpdusrId;
	private String rnum;
	private String totalRecordCount;
}
