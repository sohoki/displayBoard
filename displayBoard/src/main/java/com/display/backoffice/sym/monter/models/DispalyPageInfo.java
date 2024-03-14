package com.display.backoffice.sym.monter.models;

import java.io.Serializable;
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
public class DispalyPageInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String displaySeq ;
	private String displayTitle  = "";
	private String displayRemark = "";
	private String displayGubun = "";
	private String displayUseYn = "";
	private String frstRegistPnttm = "";
	private String frstRegistId = "";
	private String lastUpdtPnttm = "";
	private String lastUpusrId = "";
	private String mode = "";
	private String userId = "";
	
	private String displayPageCnt = "";
	private String displayTotalTime = "";
	private String updateChange = "";
	private String updateDate = "";
	private String displayGubunTxt = "";
	
	private String displayStartday = "";
	private String displayEndday = "";
	
	private int displayNextseq = 0;
	private String displayWidth = "";
	private String displayHeight = "";
	private String displayLocalfile = "";
	
	private String adminLevel = "";
	private String partId = "";
	
	
	
	
	
}
