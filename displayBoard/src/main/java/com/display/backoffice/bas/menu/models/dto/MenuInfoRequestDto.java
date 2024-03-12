package com.display.backoffice.bas.menu.models.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoRequestDto {

	@NotBlank(message="서버 이름을 입력해 주세요.")
	private String mode;
	private String menuNo = "";
	@NotBlank(message="시스템 코드를 선택해 주세요.")
	private String systemCode = "";
	@NotBlank(message="메뉴 아이디을 입력해 주세요.")
	private String menuNm = ""; 
	@NotBlank(message="프로그램을 선택해 주세요.")
	private String progrmFileNm  = "";
	private String progrmKoreannm = ""; 
	private String upperMenuNo  = "";
	private String upperMenuNm  = "";
	@NotBlank(message="메뉴 순서를 입력해 주세요.")
	private String menuOrdr  = "";
	private String menuDc  = "";
	private String menuUseyn  = "";
	private String relateImagePath = ""; 
	private String relateImageNm = "";
	private String userId;
	
	private String menuPageTarget;
	private String menuPopupnfo;
	
	private String menuPrivacy;
	
 	private int cnt = 0;
}
