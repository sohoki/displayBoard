package com.display.backoffice.bas.menu.models.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title="menuCreatdetailInfoReqDto : IPCC MQ 연동 DTO " )
public class menuCreatdetailInfoReqDto {

	@NotBlank(message="메뉴번호를 입력해 주세요.")
	@Schema(description="menuNo 메뉴 번호", example="0001")
	private String menuNo;
	
	@NotBlank(message="모드를 입력해 주세요.")
	@Schema(description="메뉴명", example="0001")
	private String menuNm; 
	
	private String progrmFileNm ;
	private String progrmKoreannm; 
	private String upperMenuNo ;
	private String upperMenuNm ;
	private String menuOrdr ;
	private String menuDc ;
	private String menuUseyn;
	private String relateImagePath; 
	private String relateImageNm;
	private String menuPageTarget;
	private String menuPopupnfo;
	private String menuPrivacy;
	private String menuBasicInfo;
	
	private String systemCode;
	/** 맵생성ID */
	private String mapCreatId;
	/** 권한코드 */
	private String roleId;
	
}
