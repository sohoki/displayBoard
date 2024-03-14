package com.display.backoffice.sts.cnt.models;

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
public class ContentFileInfo {

	private String atchFileId;
	private String fileStreCours;
	private String streFileNm;
	private String orignlFileNm;
	private String fileExtsn;
	private String fileSize;
	private String fileOrder;

	private String mode;
	private String playTime;
	private String fileWidth;
	private String fileHeight;
	private String reportSeq;
	private String userId;
	private String fileThumnail;
	private String reportGubun;
	    
	    
	
	
}
