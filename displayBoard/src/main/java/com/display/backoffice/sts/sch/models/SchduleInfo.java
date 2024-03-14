package com.display.backoffice.sts.sch.models;
import java.io.Serializable;

import com.display.backoffice.sts.message.models.SendMessageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SchduleInfo implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private String schCode = "";
	private String schTitle = "";
	private String schSendGubun = "";
	private String schFileNm = "";
	private String schStartDay = "";
	private String schEndDay = "";
	private String schStartTime = "";
	private String schEndTime = "";
	private String schUseYn = "";
	private String frstRegistPnttm = "";
	private String frstRegistId = "";
	private String lastUpdtPnttm = "";
	private String lastUpusrId = "";
	private String schMessage = "";
	private String mode = "";
	private String userId = "";
    private String schFonttype = "";
    
    
    
	
	
	public String getSchFonttype() {
		return schFonttype;
	}
	public void setSchFonttype(String schFonttype) {
		this.schFonttype = schFonttype;
	}
	public String getSchCode() {
		return schCode;
	}
	public void setSchCode(String schCode) {
		this.schCode = schCode;
	}
	public String getSchTitle() {
		return schTitle;
	}
	public void setSchTitle(String schTitle) {
		this.schTitle = schTitle;
	}
	public String getSchSendGubun() {
		return schSendGubun;
	}
	public void setSchSendGubun(String schSendGubun) {
		this.schSendGubun = schSendGubun;
	}
	public String getSchFileNm() {
		return schFileNm;
	}
	public void setSchFileNm(String schFileNm) {
		this.schFileNm = schFileNm;
	}
	public String getSchStartDay() {
		return schStartDay;
	}
	public void setSchStartDay(String schStartDay) {
		this.schStartDay = schStartDay;
	}
	public String getSchEndDay() {
		return schEndDay;
	}
	public void setSchEndDay(String schEndDay) {
		this.schEndDay = schEndDay;
	}
	public String getSchStartTime() {
		return schStartTime;
	}
	public void setSchStartTime(String schStartTime) {
		this.schStartTime = schStartTime;
	}
	public String getSchEndTime() {
		return schEndTime;
	}
	public void setSchEndTime(String schEndTime) {
		this.schEndTime = schEndTime;
	}
	public String getSchUseYn() {
		return schUseYn;
	}
	public void setSchUseYn(String schUseYn) {
		this.schUseYn = schUseYn;
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
	public String getSchMessage() {
		return schMessage;
	}
	public void setSchMessage(String schMessage) {
		this.schMessage = schMessage;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	@Override
	public String toString() {
		return "SchduleInfo [schCode=" + schCode + ", schTitle=" + schTitle
				+ ", schSendGubun=" + schSendGubun + ", schFileNm=" + schFileNm
				+ ", schStartDay=" + schStartDay + ", schEndDay=" + schEndDay
				+ ", schStartTime=" + schStartTime + ", schEndTime="
				+ schEndTime + ", schUseYn=" + schUseYn + ", frstRegistPnttm="
				+ frstRegistPnttm + ", frstRegistId=" + frstRegistId
				+ ", lastUpdtPnttm=" + lastUpdtPnttm + ", lastUpusrId="
				+ lastUpusrId + ", schMessage=" + schMessage + ", mode=" + mode
				+ ", userId=" + userId + ", schFonttype=" + schFonttype
				+ ", getSchFonttype()=" + getSchFonttype() + ", getSchCode()="
				+ getSchCode() + ", getSchTitle()=" + getSchTitle()
				+ ", getSchSendGubun()=" + getSchSendGubun()
				+ ", getSchFileNm()=" + getSchFileNm() + ", getSchStartDay()="
				+ getSchStartDay() + ", getSchEndDay()=" + getSchEndDay()
				+ ", getSchStartTime()=" + getSchStartTime()
				+ ", getSchEndTime()=" + getSchEndTime() + ", getSchUseYn()="
				+ getSchUseYn() + ", getFrstRegistPnttm()="
				+ getFrstRegistPnttm() + ", getFrstRegistId()="
				+ getFrstRegistId() + ", getLastUpdtPnttm()="
				+ getLastUpdtPnttm() + ", getLastUpusrId()=" + getLastUpusrId()
				+ ", getSchMessage()=" + getSchMessage() + ", getMode()="
				+ getMode() + ", getUserId()=" + getUserId() + "]";
	}
	

}
