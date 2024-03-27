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
	private String centerAddr1;
	private String centerAddr2;
	private String centerTel;
	private String centerFax;
	private String centerRegId;
	private String centerRegdate;
	private String centerUpdateId;
	private String centerUpdateUserId;
	private String centerUpdateDate;
	private String centerImg;
	private String centerUrl;
	private String centerSeatImg;
	private String centerUseYn;
	private String restInfo;
	private String meetingroomInfo;
	private String centerInfo;
	private String insttCode;
	
	private String adminApprovalYn;
	private String centerFloor;
	private String partId;
	private String centerFloorEnd;
	private String floorInfo;	
	
}
