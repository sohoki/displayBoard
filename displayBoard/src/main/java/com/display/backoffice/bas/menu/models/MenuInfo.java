package com.display.backoffice.bas.menu.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfo {
	
	private String menuNo = "";
	private String menuNm = ""; 
	private String systemCode = "";
	private String progrmFileNm  = "";
	private String progrmKoreannm = ""; 
	private String upperMenuNo  = "";
	private String upperMenuNm  = "";
	private String menuOrdr  = "";
	private String menuDc  = "";
	private String menuUseyn = "";
	private String relateImagePath = ""; 
	private String relateImageNm = "";
	private String menuPageTarget;
	private String menuPopupnfo;
	private String menuPrivacy;
	private int cnt = 0;
}
