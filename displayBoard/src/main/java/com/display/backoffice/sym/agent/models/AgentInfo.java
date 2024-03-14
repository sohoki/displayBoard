package com.display.backoffice.sym.agent.models;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class AgentInfo implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	private String agentCode = "";
	private String agentNm = "";
	private String agentRemark = "";
	private String agentIp = "";
	private String agentMac = "";
	private String displaySeq = "";
	private String agentUseYn = "";
	private String frstRegistPnttm = "";
	private String frstRegistId = "";
	private String lastUpdtPnttm = "";
	private String lastUpusrId = "";
	private String mode = "";
	private String userId = "";
	private String updateChange = "";
	private String updateDate = "";
	//신규 추가 
	private String centerId = "";
	private String partId = "";
	private String agentStarttime = "";
	private String agentEndtime = "";
	private String agentGubun = "";
	private int agentFloor = 0;
	private String agentContentgubun = "";
	
	//추가
	private String connDate = "";
	private String agentState = "";
	
	
	
	
	
	
	
	
	public String getConnDate() {
		return connDate;
	}
	public void setConnDate(String connDate) {
		this.connDate = connDate;
	}
	public String getAgentState() {
		return agentState;
	}
	public void setAgentState(String agentState) {
		this.agentState = agentState;
	}
	public String getAgentContentgubun() {
		return agentContentgubun;
	}
	public void setAgentContentgubun(String agentContentgubun) {
		this.agentContentgubun = agentContentgubun;
	}
	public int getAgentFloor() {
		return agentFloor;
	}
	public void setAgentFloor(int agentFloor) {
		this.agentFloor = agentFloor;
	}
	public String getAgentGubun() {
		return agentGubun;
	}
	public void setAgentGubun(String agentGubun) {
		this.agentGubun = agentGubun;
	}
	public String getAgentStarttime() {
		return agentStarttime;
	}
	public void setAgentStarttime(String agentStarttime) {
		this.agentStarttime = agentStarttime;
	}
	public String getAgentEndtime() {
		return agentEndtime;
	}
	public void setAgentEndtime(String agentEndtime) {
		this.agentEndtime = agentEndtime;
	}
	public String getCenterId() {
		return centerId;
	}
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getUpdateChange() {
		return updateChange;
	}
	public void setUpdateChange(String updateChange) {
		this.updateChange = updateChange;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentNm() {
		return agentNm;
	}
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}
	public String getAgentRemark() {
		return agentRemark;
	}
	public void setAgentRemark(String agentRemark) {
		this.agentRemark = agentRemark;
	}
	public String getAgentIp() {
		return agentIp;
	}
	public void setAgentIp(String agentIp) {
		this.agentIp = agentIp;
	}
	public String getAgentMac() {
		return agentMac;
	}
	public void setAgentMac(String agentMac) {
		this.agentMac = agentMac;
	}
	public String getDisplaySeq() {
		return displaySeq;
	}
	public void setDisplaySeq(String displaySeq) {
		this.displaySeq = displaySeq;
	}
	public String getAgentUseYn() {
		return agentUseYn;
	}
	public void setAgentUseYn(String agentUseYn) {
		this.agentUseYn = agentUseYn;
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
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}

