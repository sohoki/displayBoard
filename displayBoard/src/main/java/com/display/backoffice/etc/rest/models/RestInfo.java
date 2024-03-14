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
public class RestInfo implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String menuSeq = "";
	private String menuDate = "";
	private String menuInfo = "";
	private String menuGubun = "";
	private String menuImg = "";
	private String frstRegistPnttm;
	private String frstRegistId;
	private String lastUpdtPnttm;
	private String lastUpusrId;
	private String centerId = "";
	private String userId = "";
	private String mode = "";
	private int menuStep01Value = 0;
	private int menuStep02Value = 0;
	private int menuStep03Value = 0;
	private int menuStep04Value = 0;
	private int menuStep05Value = 0;
	private String menuUpdate  = "";
	private String menuUseyn = "";
	
	

}
