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
public class DetailPageInfo implements  Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String detailSeq;
	private String displaySeq ;
	private String reportSeq ;
	private String detailSort;
	private String detailTime;
	private String  frstRegistPnttm;
	private String  frstRegistId;
	private String  lastUpdtPnttm;
	private String  lastUpusrId;
	private String mode;
	private String userId;
	
	
	
	
	
	
}
