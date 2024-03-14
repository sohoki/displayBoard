package com.display.backoffice.uat.uia.models.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title="UserAuthInfoReqDto : 시스템별 메뉴 조회 " )
public class UserAuthInfoReqDto {

	private String userId;
	private String systemCode;
	private List<String> searchCode;
	
}
