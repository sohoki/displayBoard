package com.display.backoffice.sts.xml.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class XmlInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String xmlSeq = "";
	private String workGubun = "";
	private String xmlProcessName = "";
	private String processRemark = "";
	private String xmlInputParam = "";
	private String xmlOutputParam = "";
	private String resultCodeExample = "";
	private String etc1 = "";
	private String etc2 = "";
	private String etc3 = "";
	private String testOk = "";
	private String xmlInputParamSample = "";
	private String xmlExplain = "";
	private String mode = "";
	
	private String frstRegistPnttm = "";
	private String lastRegistPnttm = "";
	private String frstRegisterId = "";
	private String lastRegisterId = "";
	private String codeNm = "";
	
	private String protType = "";
	private String userId = "";
	private String sendGubun = "";
	

	public String getSendGubun() {
		return sendGubun;
	}

	public void setSendGubun(String sendGubun) {
		this.sendGubun = sendGubun;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getXmlSeq() {
		return xmlSeq;
	}

	public void setXmlSeq(String xmlSeq) {
		this.xmlSeq = xmlSeq;
	}

	public String getWorkGubun() {
		return workGubun;
	}

	public void setWorkGubun(String workGubun) {
		this.workGubun = workGubun;
	}

	public String getXmlProcessName() {
		return xmlProcessName;
	}

	public void setXmlProcessName(String xmlProcessName) {
		this.xmlProcessName = xmlProcessName;
	}

	public String getProcessRemark() {
		return processRemark;
	}

	public void setProcessRemark(String processRemark) {
		this.processRemark = processRemark;
	}

	public String getXmlInputParam() {
		return xmlInputParam;
	}

	public void setXmlInputParam(String xmlInputParam) {
		this.xmlInputParam = xmlInputParam;
	}

	public String getXmlOutputParam() {
		return xmlOutputParam;
	}

	public void setXmlOutputParam(String xmlOutputParam) {
		this.xmlOutputParam = xmlOutputParam;
	}

	public String getResultCodeExample() {
		return resultCodeExample;
	}

	public void setResultCodeExample(String resultCodeExample) {
		this.resultCodeExample = resultCodeExample;
	}

	public String getEtc1() {
		return etc1;
	}

	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	public String getEtc2() {
		return etc2;
	}

	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	public String getEtc3() {
		return etc3;
	}

	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	public String getTestOk() {
		return testOk;
	}

	public void setTestOk(String testOk) {
		this.testOk = testOk;
	}

	public String getXmlInputParamSample() {
		return xmlInputParamSample;
	}

	public void setXmlInputParamSample(String xmlInputParamSample) {
		this.xmlInputParamSample = xmlInputParamSample;
	}

	public String getXmlExplain() {
		return xmlExplain;
	}

	public void setXmlExplain(String xmlExplain) {
		this.xmlExplain = xmlExplain;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}

	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}

	public String getLastRegistPnttm() {
		return lastRegistPnttm;
	}

	public void setLastRegistPnttm(String lastRegistPnttm) {
		this.lastRegistPnttm = lastRegistPnttm;
	}

	public String getFrstRegisterId() {
		return frstRegisterId;
	}

	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}

	public String getLastRegisterId() {
		return lastRegisterId;
	}

	public void setLastRegisterId(String lastRegisterId) {
		this.lastRegisterId = lastRegisterId;
	}

	public String getCodeNm() {
		return codeNm;
	}

	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}

	public String getProtType() {
		return protType;
	}

	public void setProtType(String protType) {
		this.protType = protType;
	}
	
}

