package com.display.backoffice.uat.uia.models;

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
public class PartInfo implements Serializable{

	private String partId;
	private String parentPartId;
	private String partNm;
	private String partDc;
	private String partUseyn;
	private String partOrder;
	private String mode;
	private String partLevel;
	private String partCreateDe;
	private String userId;
	private String insttCode;
	private String partEndDe;
	private String partHeadUserId;
	private String partEndyn;
	
	

}
