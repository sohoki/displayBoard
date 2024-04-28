package com.display.backoffice.bas.code.models.dto;

import com.display.backoffice.bas.code.models.CmmnClCode;

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
public class CmmnClCodeResDto {

	private String clCode = "";
	
    private String clCodeNm = "";
    
    private String clCodeDc = "";
    
    private String useAt = "";
    
    private String frstRegisterId = "";
    
    private String lastUpdusrId   = "";
    
    private String frstRegistPnttm = "";
    
    private String lastUpdtPnttm = "";
    
    private String menuGubun;	
    
    private String mode;
    
    private String userId;
    
    private String systemCode;
    
    private String totalRecordCount;
    
    
}
