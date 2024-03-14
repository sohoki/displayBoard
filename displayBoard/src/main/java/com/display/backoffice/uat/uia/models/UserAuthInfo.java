package com.display.backoffice.uat.uia.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title="UserAuthInfo : 시스템별 관리자 리스트 " )
public class UserAuthInfo {
	
	private String systemCode;
	private String authGubun;
	private String userId;
	private String roleId;
	private String frstRegistPnttm;
	private String frstRegisterId;
	private String lastUpdtPnttm;
	private String lastUpdusrId;
	private String authDc;
}
