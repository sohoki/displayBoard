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
public class CmmnDetailCode implements Serializable{

	private static final long serialVersionUID = 1L;

	private String mode;
	private String codeId;
	private String code;
    private String codeNm;
    private String codeDc;
    private String useAt;
    private String frstRegisterId;
    private String lastUpdusrId;
    private String codeEtc1;
    private String codeEtc2;
    private String userId;
    private String systemCode;
}
