package com.display.backoffice.bas.cnt.models;

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
public class CenterInfo  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String centerId;
	private String centerNm;
	private String centerZipcode;
	private String centerZipcode1;
	private String centerZipcode2;
	private String centerAddr1;
	private String centerAddr2;
	private String centerTel;
	private String centerFax;
	private String centerRegId;
	private String centerRegdate;
	private String centerUpdateId;
	private String centerImg;
	private String centerUrl;
	private String centerSeatImg;
	private String centerUseYn;
	private String restInfo;
	private String meetingroomInfo;
	private String centerInfo;
	
	private String adminApprovalYn;
	private String centerFloor;
	private String mode;
	private String partId;
	private String centerFloorEnd;
	private String totalRecordCount;
	
	
	

	
	
	
	
	
}
