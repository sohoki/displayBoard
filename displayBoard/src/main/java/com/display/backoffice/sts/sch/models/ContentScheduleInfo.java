package com.display.backoffice.sts.sch.models;

import java.io.Serializable;

import com.display.backoffice.sts.message.models.SendMessageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ContentScheduleInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String conschCode = "";
	private String conschTitle = "";
	private String conschStartday = "";
	private String conschEndday = "";
    private String displaySeq = "";
    private String conschUseyn  = "";
    private int conschAgentcnt = 0;
    private String partId = "";
    private String adminLevel = "";
    
	
	private String frstRegistPnttm = "";
	private String frstRegistId = "";
	private String lastUpdtPnttm = "";
	private String lastUpusrId = "";
	
	private String conschUnisinkgubun = "";
	private String conschUnisinkcode = "";
	private int conschUnisinkorder = 0;
	private String userId = "";
	private String mode;
    
    
	public String getAdminLevel() {
		return adminLevel;
	}
	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getConschUnisinkgubun() {
		return conschUnisinkgubun;
	}
	public void setConschUnisinkgubun(String conschUnisinkgubun) {
		this.conschUnisinkgubun = conschUnisinkgubun;
	}
	public String getConschUnisinkcode() {
		return conschUnisinkcode;
	}
	public void setConschUnisinkcode(String conschUnisinkcode) {
		this.conschUnisinkcode = conschUnisinkcode;
	}
	public int getConschUnisinkorder() {
		return conschUnisinkorder;
	}
	public void setConschUnisinkorder(int conschUnisinkorder) {
		this.conschUnisinkorder = conschUnisinkorder;
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
	public String getConschCode() {
		return conschCode;
	}
	public void setConschCode(String conschCode) {
		this.conschCode = conschCode;
	}
	public String getConschTitle() {
		return conschTitle;
	}
	public void setConschTitle(String conschTitle) {
		this.conschTitle = conschTitle;
	}
	public String getConschStartday() {
		return conschStartday;
	}
	public void setConschStartday(String conschStartday) {
		this.conschStartday = conschStartday;
	}
	public String getConschEndday() {
		return conschEndday;
	}
	public void setConschEndday(String conschEndday) {
		this.conschEndday = conschEndday;
	}
	public String getDisplaySeq() {
		return displaySeq;
	}
	public void setDisplaySeq(String displaySeq) {
		this.displaySeq = displaySeq;
	}
	public String getConschUseyn() {
		return conschUseyn;
	}
	public void setConschUseyn(String conschUseyn) {
		this.conschUseyn = conschUseyn;
	}
	public int getConschAgentcnt() {
		return conschAgentcnt;
	}
	public void setConschAgentcnt(int conschAgentcnt) {
		this.conschAgentcnt = conschAgentcnt;
	}
	
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	@Override
	public String toString() {
		return "ContentScheduleInfo [conschCode=" + conschCode
				+ ", conschTitle=" + conschTitle + ", conschStartday="
				+ conschStartday + ", conschEndday=" + conschEndday
				+ ", displaySeq=" + displaySeq + ", conschUseyn=" + conschUseyn
				+ ", conschAgentcnt=" + conschAgentcnt + ", partId=" + partId
				+ ", adminLevel=" + adminLevel + ", frstRegistPnttm="
				+ frstRegistPnttm + ", frstRegistId=" + frstRegistId
				+ ", lastUpdtPnttm=" + lastUpdtPnttm + ", lastUpusrId="
				+ lastUpusrId + ", conschUnisinkgubun=" + conschUnisinkgubun
				+ ", conschUnisinkcode=" + conschUnisinkcode
				+ ", conschUnisinkorder=" + conschUnisinkorder + ", userId="
				+ userId + ", mode=" + mode + "]";
	}
    
    
    
    
	
}
