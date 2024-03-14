package com.display.backoffice.etc.rest.models;

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

public class RestNoticeInfo  implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String noteSeq = "";
	private String noteTitle = "";
	private String noteStartdate = "";
	private String noteEnddate = "";
	private String noteContent = "";
	private String  frstRegistPnttm= "";
	private String  frstRegistId= "";
	private String  lastUpdtPnttm= "";
	private String  lastUpusrId= "";
	private String mode= "";
	private String userId = "";
	
	
	
	
	

}
