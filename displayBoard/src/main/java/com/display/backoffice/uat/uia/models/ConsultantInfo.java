package com.display.backoffice.uat.uia.models;

import java.util.List;

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
public class ConsultantInfo {

	private String pbxExtension;
	private String pbxType;
	private String pbxCor;
	private String pbxCos;
	private String pbxName;
	private String pbxSecurityCode;
	
	private String pbxLoginId;
	private String pbxStatus;
	private String pbxRegDate;
	private String pbxUpdateDate;
	private String ctiCenterId;
	private String ctiTenantId;
	private String ctiEmployeegrpid;
	private String ctiEmployeepartid;
	private String ctiEmployeeid;
	private String ctiLoginid;
	private String ctiName;
	private String ctiPassword;
	private String ctiBlendKind;
	
	private String ctiCenterName;
	private String ctiEmployeegrpName;
	private String ctiEmployeepartName;
	private String ctiTenantName;
	//강제 입력 
	private String ctiPermitId;
	private String ctiMoniterFlag;
	private String ctiDefaultQueue;
	
	
	private String ctiRegDate;
	private String ctiUpdateDate;
	private String counStatus;
	private String frstRegistPnttm;
	private String frstRegisterId;
	private String lastUpdtPnttm;
	private String lastUpdusrId;
	private String counRemark;
	private String counUseyn;
	
	//신규 업데이트 
	private String pbxButton01;
	private String pbxButton02;
	private String pbxButton03;
	private String pbxButton04;
	private String pbxButton05;
	private String pbxButton06;
	private String pbxButton07;
	private String pbxDisplayLangage;
	
	//신규
	private String partId;
	private String insttCode;
	
	
}
