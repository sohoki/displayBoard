package com.display.backoffice.uat.uia.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(title="UserAuthInfo : 시스템별 관리자 리스트 " )
public class UserAuthInfoDto {

	private String systemCode;
	private String systemName;
	private String authGubun;
	private String userId;
	private String adminId;
	private String roleId;
	private String roleName;
	private String frstRegistPnttm;
	private String frstRegisterId;
	private String lastUpdtPnttm;
	private String lastUpdusrId;
	private String authDc;	
}
