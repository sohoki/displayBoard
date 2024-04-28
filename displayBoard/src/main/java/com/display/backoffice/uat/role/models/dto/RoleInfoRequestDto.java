package com.display.backoffice.uat.role.models.dto;

import javax.validation.constraints.NotBlank;

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
public class RoleInfoRequestDto {

	@NotBlank(message="등록구분을 입력해 주세요.")
	private String mode;
	
	@NotBlank(message="ROLE ID 입력해 주세요.")
	private String roleId;
	@NotBlank(message="ROLE 명을 입력해 주세요.")
	private String roleName;
	private String userId;
	private String roleDc;
	@NotBlank(message="시스템 코드를 입력해 주세요.")
	private String systemCode;
	private String roleUseyn;
	private String frstRegistPnttm;
	private String frstRegisterId;
	private String lastUpdtPnttm;
	private String lastUpdusrId;
}
