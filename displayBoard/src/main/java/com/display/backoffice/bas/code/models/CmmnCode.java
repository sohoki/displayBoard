package com.display.backoffice.bas.code.models;

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
public class CmmnCode implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String clCode;
	private String mode;
	private String codeId;
	private String codeIdNm;
	private String codeIdDc;
    private String useAt;
    private String menuGubun;
    private String frstRegisterId;
    private String lastUpdusrId;
    private String userId;
    private String systemCode;
    
}
