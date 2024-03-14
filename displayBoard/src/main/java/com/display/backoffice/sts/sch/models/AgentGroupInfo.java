package com.display.backoffice.sts.sch.models;

import java.io.Serializable;

import com.display.backoffice.sts.message.models.SendMessageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class AgentGroupInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int groupSeq =0;
	private String conschCode = "";
	private String agentCode = "";
	private String sendDate = "";
	private String sendResult = "";
	private String receivedDate = "";
	private String receivedResult = "";
	private String frstRegistPnttm = "";
	private String frstRegistId = "";
	private String lastUpdtPnttm = "";
	private String lastUpusrId = "";
	private String receivedFileDate = "";
	private String receivedFileResult = "";
	private String mode = "";
	private String userId = "";
	
	
	
	
	
	public String getSendResult() {
		return sendResult;
	}
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getGroupSeq() {
		return groupSeq;
	}
	public void setGroupSeq(int groupSeq) {
		this.groupSeq = groupSeq;
	}
	public String getConschCode() {
		return conschCode;
	}
	public void setConschCode(String conschCode) {
		this.conschCode = conschCode;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getReceivedResult() {
		return receivedResult;
	}
	public void setReceivedResult(String receivedResult) {
		this.receivedResult = receivedResult;
	}
	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegistId() {
		return frstRegistId;
	}
	public void setFrstRegistId(String frstRegistId) {
		this.frstRegistId = frstRegistId;
	}
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getLastUpusrId() {
		return lastUpusrId;
	}
	public void setLastUpusrId(String lastUpusrId) {
		this.lastUpusrId = lastUpusrId;
	}
	public String getReceivedFileDate() {
		return receivedFileDate;
	}
	public void setReceivedFileDate(String receivedFileDate) {
		this.receivedFileDate = receivedFileDate;
	}
	public String getReceivedFileResult() {
		return receivedFileResult;
	}
	public void setReceivedFileResult(String receivedFileResult) {
		this.receivedFileResult = receivedFileResult;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	@Override
	public String toString() {
		return "AgentGroupInfo [groupSeq=" + groupSeq + ", conschCode="
				+ conschCode + ", agentCode=" + agentCode + ", sendDate="
				+ sendDate + ", sendResult=" + sendResult + ", receivedDate="
				+ receivedDate + ", receivedResult=" + receivedResult
				+ ", frstRegistPnttm=" + frstRegistPnttm + ", frstRegistId="
				+ frstRegistId + ", lastUpdtPnttm=" + lastUpdtPnttm
				+ ", lastUpusrId=" + lastUpusrId + ", receivedFileDate="
				+ receivedFileDate + ", receivedFileResult="
				+ receivedFileResult + ", mode=" + mode + ", userId=" + userId
				+ ", getSendResult()=" + getSendResult() + ", getUserId()="
				+ getUserId() + ", getGroupSeq()=" + getGroupSeq()
				+ ", getConschCode()=" + getConschCode() + ", getAgentCode()="
				+ getAgentCode() + ", getSendDate()=" + getSendDate()
				+ ", getReceivedDate()=" + getReceivedDate()
				+ ", getReceivedResult()=" + getReceivedResult()
				+ ", getFrstRegistPnttm()=" + getFrstRegistPnttm()
				+ ", getFrstRegistId()=" + getFrstRegistId()
				+ ", getLastUpdtPnttm()=" + getLastUpdtPnttm()
				+ ", getLastUpusrId()=" + getLastUpusrId()
				+ ", getReceivedFileDate()=" + getReceivedFileDate()
				+ ", getReceivedFileResult()=" + getReceivedFileResult()
				+ ", getMode()=" + getMode() + "]";
	}
	
	
	
	
	
}

