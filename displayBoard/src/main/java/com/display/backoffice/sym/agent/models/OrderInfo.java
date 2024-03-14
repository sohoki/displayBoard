package com.display.backoffice.sym.agent.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderInfo implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String orderSeq;
	private String agentCode;
	private String agentOrder;
	private String agentOrderCheck;
	private String agentSendDate;
	private String agentOrderUpdate;
	
	
	public String getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentOrder() {
		return agentOrder;
	}
	public void setAgentOrder(String agentOrder) {
		this.agentOrder = agentOrder;
	}
	public String getAgentOrderCheck() {
		return agentOrderCheck;
	}
	public void setAgentOrderCheck(String agentOrderCheck) {
		this.agentOrderCheck = agentOrderCheck;
	}
	public String getAgentSendDate() {
		return agentSendDate;
	}
	public void setAgentSendDate(String agentSendDate) {
		this.agentSendDate = agentSendDate;
	}
	public String getAgentOrderUpdate() {
		return agentOrderUpdate;
	}
	public void setAgentOrderUpdate(String agentOrderUpdate) {
		this.agentOrderUpdate = agentOrderUpdate;
	}
	
	
}

