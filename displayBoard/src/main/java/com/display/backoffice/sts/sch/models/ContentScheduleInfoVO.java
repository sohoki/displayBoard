package com.display.backoffice.sts.sch.models;

import java.io.Serializable;

import com.display.backoffice.sts.message.models.SendMessageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ContentScheduleInfoVO extends ContentScheduleInfo implements Serializable {
	
	
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
    private String displayTitle = "";
    private String partNm = "";
    private String conschUnisinkgubunTxt = "";
    private int totalRecordCount = 0; 
    
    private String reportTitle;
    private String agentCnt = "";
    
    private String displayPagecnt = "";
    private String displayTotaltime = "";
    private String displayGubun = "";
   
	public String getDisplayGubun() {
		return displayGubun;
	}
	public void setDisplayGubun(String displayGubun) {
		this.displayGubun = displayGubun;
	}
	public String getDisplayPagecnt() {
		return displayPagecnt;
	}
	public void setDisplayPagecnt(String displayPagecnt) {
		this.displayPagecnt = displayPagecnt;
	}
	public String getDisplayTotaltime() {
		return displayTotaltime;
	}
	public void setDisplayTotaltime(String displayTotaltime) {
		this.displayTotaltime = displayTotaltime;
	}
	public String getAgentCnt() {
		return agentCnt;
	}
	public void setAgentCnt(String agentCnt) {
		this.agentCnt = agentCnt;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public String getConschUnisinkgubunTxt() {
		return conschUnisinkgubunTxt;
	}
	public void setConschUnisinkgubunTxt(String conschUnisinkgubunTxt) {
		this.conschUnisinkgubunTxt = conschUnisinkgubunTxt;
	}
	public String getPartNm() {
		return partNm;
	}
	public void setPartNm(String partNm) {
		this.partNm = partNm;
	}
	public String getDisplayTitle() {
		return displayTitle;
	}
	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
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
		return "ContentScheduleInfoVO [searchCondition=" + searchCondition
				+ ", searchKeyword=" + searchKeyword + ", searchUseYn="
				+ searchUseYn + ", mber_Sttus=" + mber_Sttus + ", pageIndex="
				+ pageIndex + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + ", firstIndex=" + firstIndex + ", lastIndex="
				+ lastIndex + ", recordCountPerPage=" + recordCountPerPage
				+ ", displayTitle=" + displayTitle + ", partNm=" + partNm
				+ ", conschUnisinkgubunTxt=" + conschUnisinkgubunTxt
				+ ", totalRecordCount=" + totalRecordCount + ", reportTitle="
				+ reportTitle + ", agentCnt=" + agentCnt + ", displayPagecnt="
				+ displayPagecnt + ", displayTotaltime=" + displayTotaltime
				+ ", displayGubun=" + displayGubun + ", getDisplayGubun()="
				+ getDisplayGubun() + ", getDisplayPagecnt()="
				+ getDisplayPagecnt() + ", getDisplayTotaltime()="
				+ getDisplayTotaltime() + ", getAgentCnt()=" + getAgentCnt()
				+ ", getReportTitle()=" + getReportTitle()
				+ ", getConschUseyn()=" + getConschUseyn()
				+ ", getTotalRecordCount()=" + getTotalRecordCount()
				+ ", getConschUnisinkgubunTxt()=" + getConschUnisinkgubunTxt()
				+ ", getPartNm()=" + getPartNm() + ", getDisplayTitle()="
				+ getDisplayTitle() + ", getSearchCondition()="
				+ getSearchCondition() + ", getSearchKeyword()="
				+ getSearchKeyword() + ", getSearchUseYn()=" + getSearchUseYn()
				+ ", getMber_Sttus()=" + getMber_Sttus() + ", getPageIndex()="
				+ getPageIndex() + ", getPageUnit()=" + getPageUnit()
				+ ", getPageSize()=" + getPageSize() + ", getFirstIndex()="
				+ getFirstIndex() + ", getLastIndex()=" + getLastIndex()
				+ ", getRecordCountPerPage()=" + getRecordCountPerPage() + "]";
	}
    
    
    
    
}
