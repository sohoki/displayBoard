package com.display.backoffice.sym.monter.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor	
public class DispalyPageInfoVO extends DispalyPageInfo implements  Serializable{

	
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
    private int subpageIndex = 1; 
    /** 페이지갯수 */
    private int pageUnit = 10;    
    /** 페이지사이즈 */
    private int pageSize = 10;    
    private int firstIndex = 1;
    private int lastIndex = 1;    
    private int recordCountPerPage = 10;
    
    private String reportTitle;
    private String detailSeq_N;
    private int totalRecordCount;
    

  	private String subsearchCondition = "";
  	private String subsearchKeyword = "";
    private String displayNextSeqTxt = "";
    private String partNm = "";
    private String deptLevel = "";
    
    private String searchPartid;
    private String searchCenterid;
    private String searchFloor;
    private String searchAgentgubun;

    
	public String getDeptLevel() {
		return deptLevel;
	}
	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}
	public String getPartNm() {
		return partNm;
	}
	public void setPartNm(String partNm) {
		this.partNm = partNm;
	}
	public String getDisplayNextSeqTxt() {
		return displayNextSeqTxt;
	}
	public void setDisplayNextSeqTxt(String displayNextSeqTxt) {
		this.displayNextSeqTxt = displayNextSeqTxt;
	}
	public String getSubsearchCondition() {
		return subsearchCondition;
	}
	public void setSubsearchCondition(String subsearchCondition) {
		this.subsearchCondition = subsearchCondition;
	}
	public String getSubsearchKeyword() {
		return subsearchKeyword;
	}
	public void setSubsearchKeyword(String subsearchKeyword) {
		this.subsearchKeyword = subsearchKeyword;
	}
	
	
	
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public int getSubpageIndex() {
		return subpageIndex;
	}
	public void setSubpageIndex(int subpageIndex) {
		this.subpageIndex = subpageIndex;
	}
	public String getDetailSeq_N() {
		return detailSeq_N;
	}
	public void setDetailSeq_N(String detailSeq_N) {
		this.detailSeq_N = detailSeq_N;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
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
		return "DispalyPageInfoVO [searchCondition=" + searchCondition
				+ ", searchKeyword=" + searchKeyword + ", searchUseYn="
				+ searchUseYn + ", mber_Sttus=" + mber_Sttus + ", pageIndex="
				+ pageIndex + ", subpageIndex=" + subpageIndex + ", pageUnit="
				+ pageUnit + ", pageSize=" + pageSize + ", firstIndex="
				+ firstIndex + ", lastIndex=" + lastIndex
				+ ", recordCountPerPage=" + recordCountPerPage
				+ ", reportTitle=" + reportTitle + ", detailSeq_N="
				+ detailSeq_N + ", totalRecordCount=" + totalRecordCount
				+ ", subsearchCondition=" + subsearchCondition
				+ ", subsearchKeyword=" + subsearchKeyword
				+ ", displayNextSeqTxt=" + displayNextSeqTxt + ", partNm="
				+ partNm + ", deptLevel=" + deptLevel
				+ ", getDisplayLocalfile()=" + getDisplayLocalfile()
				+ ", getAdminLevel()=" + getAdminLevel() + ", getPartId()="
				+ getPartId() + ", getDisplayGubun()=" + getDisplayGubun()
				+ ", getDisplayNextseq()=" + getDisplayNextseq()
				+ ", getDisplayWidth()=" + getDisplayWidth()
				+ ", getDisplayHeight()=" + getDisplayHeight()
				+ ", getDisplayStartday()=" + getDisplayStartday()
				+ ", getDisplayEndday()=" + getDisplayEndday()
				+ ", getDisplayGubunTxt()=" + getDisplayGubunTxt()
				+ ", getUpdateChange()=" + getUpdateChange()
				+ ", getUpdateDate()=" + getUpdateDate()
				+ ", getDisplayPageCnt()=" + getDisplayPageCnt()
				+ ", getDisplayTotalTime()=" + getDisplayTotalTime()
				+ ", getDisplayUseYn()=" + getDisplayUseYn() + ", getUserId()="
				+ getUserId() + ", getDisplaySeq()=" + getDisplaySeq()
				+ ", getDisplayTitle()=" + getDisplayTitle()
				+ ", getDisplayRemark()=" + getDisplayRemark()
				+ ", getFrstRegistPnttm()=" + getFrstRegistPnttm()
				+ ", getFrstRegistId()=" + getFrstRegistId()
				+ ", getLastUpdtPnttm()=" + getLastUpdtPnttm()
				+ ", getLastUpusrId()=" + getLastUpusrId() + ", getMode()="
				+ getMode() + "]";
	}
    
    
    
    
}
