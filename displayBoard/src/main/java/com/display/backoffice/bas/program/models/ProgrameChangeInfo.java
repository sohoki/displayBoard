package com.display.backoffice.bas.program.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgrameChangeInfo {

	private String progrmFileNm = "";
	private String rqesterNo = "";
	private String rqesterId = "";
	private String changerqesterCn = "";
	private String rqesterProcessCn = "";
	private String opetrId = "";
	private String processSttusCode = "";
	private String processDe = "";
	private String rqesterDe = "";
	private String rqesterSj = "";
	private String mode = "";
}
