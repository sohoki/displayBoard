package com.display.backoffice.sts.message.models;

import java.io.Serializable;

import com.display.backoffice.sts.error.models.ErrorInfo;

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
public class SendMessageInfo implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mode;
	private String sendMsgSeq;
	private String schCode;
	private String agentCode;
	private String msgSend;
	private String msgSendRegdate;
	private String msgRecCheck;
	private String msgRecUpdate;
	
	
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getSendMsgSeq() {
		return sendMsgSeq;
	}
	public void setSendMsgSeq(String sendMsgSeq) {
		this.sendMsgSeq = sendMsgSeq;
	}
	public String getSchCode() {
		return schCode;
	}
	public void setSchCode(String schCode) {
		this.schCode = schCode;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getMsgSend() {
		return msgSend;
	}
	public void setMsgSend(String msgSend) {
		this.msgSend = msgSend;
	}
	public String getMsgSendRegdate() {
		return msgSendRegdate;
	}
	public void setMsgSendRegdate(String msgSendRegdate) {
		this.msgSendRegdate = msgSendRegdate;
	}
	public String getMsgRecCheck() {
		return msgRecCheck;
	}
	public void setMsgRecCheck(String msgRecCheck) {
		this.msgRecCheck = msgRecCheck;
	}
	public String getMsgRecUpdate() {
		return msgRecUpdate;
	}
	public void setMsgRecUpdate(String msgRecUpdate) {
		this.msgRecUpdate = msgRecUpdate;
	}
	
	
	
	
	
	
}
