package com.display.backoffice.sts.xml.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class SendMsgInfo  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String msgSeq = "";
	private String agentCode = "";
	private String xmlProcessName = "";
	private String sendResult = "";
	private String sendRegdate = "";
	private String agentMac = "";
	private String agentPlaytime = "";
	private String errorMessage = "";
	private String frstRegistPnttm = "";
	private String frstRegisterId = "";
	private String mode = "";
	
	
	public String getMsgSeq() {
		return msgSeq;
	}
	public void setMsgSeq(String msgSeq) {
		this.msgSeq = msgSeq;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getXmlProcessName() {
		return xmlProcessName;
	}
	public void setXmlProcessName(String xmlProcessName) {
		this.xmlProcessName = xmlProcessName;
	}
	public String getSendResult() {
		return sendResult;
	}
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	public String getSendRegdate() {
		return sendRegdate;
	}
	public void setSendRegdate(String sendRegdate) {
		this.sendRegdate = sendRegdate;
	}
	public String getAgentMac() {
		return agentMac;
	}
	public void setAgentMac(String agentMac) {
		this.agentMac = agentMac;
	}
	public String getAgentPlaytime() {
		return agentPlaytime;
	}
	public void setAgentPlaytime(String agentPlaytime) {
		this.agentPlaytime = agentPlaytime;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	@Override
	public String toString() {
		return "SendMsgInfo [msgSeq=" + msgSeq + ", agentCode=" + agentCode
				+ ", xmlProcessName=" + xmlProcessName + ", sendResult="
				+ sendResult + ", sendRegdate=" + sendRegdate + ", agentMac="
				+ agentMac + ", agentPlaytime=" + agentPlaytime
				+ ", errorMessage=" + errorMessage + ", frstRegistPnttm="
				+ frstRegistPnttm + ", frstRegisterId=" + frstRegisterId
				+ ", mode=" + mode + ", getMsgSeq()=" + getMsgSeq()
				+ ", getAgentCode()=" + getAgentCode()
				+ ", getXmlProcessName()=" + getXmlProcessName()
				+ ", getSendResult()=" + getSendResult()
				+ ", getSendRegdate()=" + getSendRegdate() + ", getAgentMac()="
				+ getAgentMac() + ", getAgentPlaytime()=" + getAgentPlaytime()
				+ ", getErrorMessage()=" + getErrorMessage()
				+ ", getFrstRegistPnttm()=" + getFrstRegistPnttm()
				+ ", getFrstRegisterId()=" + getFrstRegisterId()
				+ ", getMode()=" + getMode() + "]";
	}
	
	
	

}
