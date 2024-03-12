package com.display.backoffice.bas.code.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmmnClCode implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String clCode = "";
	
    private String clCodeNm = "";
    
    private String clCodeDc = "";
    
    private String useAt = "";
    
    private String frstRegisterId = "";
    
    private String lastUpdusrId   = "";
    
    private String menuGubun;	
    
    private String mode;
    
    private String userId;
    
    private String systemCode;

}
