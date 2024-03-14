package com.display.backoffice.sts.sch.models;

import java.io.Serializable;

import com.display.backoffice.sts.message.models.SendMessageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class SchduleInfoVO extends SchduleInfo implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;
	/** 검색조건 */
    private String searchCondition = "";    
    /** 검색Keyword */
    private String searchKeyword = "";    
    /** 검색사용여부 */
    private String searchUseYn = "";    
    
    private String mber_Sttus = "";
    
    
    /** 현재페이지 */
    private int pageIndex = 1;    
    /** 페이지갯수 */
    private int pageUnit = 10;    
    /** 페이지사이즈 */
    private int pageSize = 10;    
    private int firstIndex = 1;
    private int lastIndex = 1;    
    private int recordCountPerPage = 10;
    
    private String schStartTime1;
    private String schStartTime2;
    
    private String schEndTime1;
    private String schEndTime2;
    private String schSendGubunTxt;
    private String agentCode;
    private String agentNm;
    private int totalRecordCount;
    private String agentCodeLst;
    
    
    
	
	public String getAgentCodeLst() {
		return agentCodeLst;
	}
	public void setAgentCodeLst(String agentCodeLst) {
		this.agentCodeLst = agentCodeLst;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public String getAgentNm() {
		return agentNm;
	}
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getSchSendGubunTxt() {
		return schSendGubunTxt;
	}
	public void setSchSendGubunTxt(String schSendGubunTxt) {
		this.schSendGubunTxt = schSendGubunTxt;
	}
	public String getSchStartTime1() {
		return schStartTime1;
	}
	public void setSchStartTime1(String schStartTime1) {
		this.schStartTime1 = schStartTime1;
	}
	public String getSchStartTime2() {
		return schStartTime2;
	}
	public void setSchStartTime2(String schStartTime2) {
		this.schStartTime2 = schStartTime2;
	}
	public String getSchEndTime1() {
		return schEndTime1;
	}
	public void setSchEndTime1(String schEndTime1) {
		this.schEndTime1 = schEndTime1;
	}
	public String getSchEndTime2() {
		return schEndTime2;
	}
	public void setSchEndTime2(String schEndTime2) {
		this.schEndTime2 = schEndTime2;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSearchUseYn() {
		return searchUseYn;
	}
	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}
	public String getMber_Sttus() {
		return mber_Sttus;
	}
	public void setMber_Sttus(String mber_Sttus) {
		this.mber_Sttus = mber_Sttus;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
   	@Override
	public String toString() {
		return "SchduleInfoVO [searchCondition=" + searchCondition
				+ ", searchKeyword=" + searchKeyword + ", searchUseYn="
				+ searchUseYn + ", mber_Sttus=" + mber_Sttus + ", pageIndex="
				+ pageIndex + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + ", firstIndex=" + firstIndex + ", lastIndex="
				+ lastIndex + ", recordCountPerPage=" + recordCountPerPage
				+ ", schStartTime1=" + schStartTime1 + ", schStartTime2="
				+ schStartTime2 + ", schEndTime1=" + schEndTime1
				+ ", schEndTime2=" + schEndTime2 + ", schSendGubunTxt="
				+ schSendGubunTxt + ", agentCode=" + agentCode + ", agentNm="
				+ agentNm + ", totalRecordCount=" + totalRecordCount
				+ ", agentCodeLst=" + agentCodeLst + ", getSchFonttype()="
				+ getSchFonttype() + ", getSchCode()=" + getSchCode()
				+ ", getSchTitle()=" + getSchTitle() + ", getSchSendGubun()="
				+ getSchSendGubun() + ", getSchFileNm()=" + getSchFileNm()
				+ ", getSchStartDay()=" + getSchStartDay()
				+ ", getSchEndDay()=" + getSchEndDay() + ", getSchStartTime()="
				+ getSchStartTime() + ", getSchEndTime()=" + getSchEndTime()
				+ ", getSchUseYn()=" + getSchUseYn()
				+ ", getFrstRegistPnttm()=" + getFrstRegistPnttm()
				+ ", getFrstRegistId()=" + getFrstRegistId()
				+ ", getLastUpdtPnttm()=" + getLastUpdtPnttm()
				+ ", getLastUpusrId()=" + getLastUpusrId()
				+ ", getSchMessage()=" + getSchMessage() + ", getMode()="
				+ getMode() + ", getUserId()=" + getUserId() + "]";
	}
    
}
