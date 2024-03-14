package com.display.backoffice.sts.report.models;
import java.io.Serializable;

import com.display.backoffice.sts.message.models.SendMessageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ReportPageInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String reportSeq ;
	private String  reportTitle ;
	private String  reportDc ;
	private String  reportUrl;
	private String  reportGubun;
	private String  reportUseYn;
	private String  frstRegistPnttm;
	private String  frstRegistId;
	private String  lastUpdtPnttm;
	private String  lastUpusrId;
	private String mode;
	private String userId;
	private int totalRecordCount;//  TOTAL_RECORD_COUNT
	private String reportPreview;
	private String atchFileId = "";
	
	
	
	
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getReportPreview() {
		return reportPreview;
	}
	public void setReportPreview(String reportPreview) {
		this.reportPreview = reportPreview;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
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
	public String getReportSeq() {
		return reportSeq;
	}
	public void setReportSeq(String reportSeq) {
		this.reportSeq = reportSeq;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public String getReportDc() {
		return reportDc;
	}
	public void setReportDc(String reportDc) {
		this.reportDc = reportDc;
	}
	public String getReportUrl() {
		return reportUrl;
	}
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
	public String getReportGubun() {
		return reportGubun;
	}
	public void setReportGubun(String reportGubun) {
		this.reportGubun = reportGubun;
	}
	public String getReportUseYn() {
		return reportUseYn;
	}
	public void setReportUseYn(String reportUseYn) {
		this.reportUseYn = reportUseYn;
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
	
	
	
	
}

