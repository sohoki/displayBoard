package com.display.backoffice.sts.report.models;

import java.io.Serializable;
import java.util.List;

import com.display.backoffice.sts.message.models.SendMessageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class ReportPageInfoVO extends ReportPageInfo implements  Serializable {

	
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
    
    private String replacePath = "";
    private String searchGubun = "";
    private List<String> searchReportGubun = null;
    private String reportUrlReal= ""; 
    
    //신규 적용
    private String adminLevel = "";
    private String partId = "";
    
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getAdminLevel() {
		return adminLevel;
	}
	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}
	//신규 적용 끝 부분	
	public List<String> getSearchReportGubun() {
		return searchReportGubun;
	}
	public void setSearchReportGubun(List<String> searchReportGubun) {
		this.searchReportGubun = searchReportGubun;
	}
	
	public String getReportUrlReal() {
		return reportUrlReal;
	}
	public void setReportUrlReal(String reportUrlReal) {
		this.reportUrlReal = reportUrlReal;
	}
	
	public String getSearchGubun() {
		return searchGubun;
	}
	public void setSearchGubun(String searchGubun) {
		this.searchGubun = searchGubun;
	}
	public String getReplacePath() {
		return replacePath;
	}
	public void setReplacePath(String replacePath) {
		this.replacePath = replacePath;
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
		return "ReportPageInfoVO [searchCondition=" + searchCondition
				+ ", searchKeyword=" + searchKeyword + ", searchUseYn="
				+ searchUseYn + ", mber_Sttus=" + mber_Sttus + ", pageIndex="
				+ pageIndex + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + ", firstIndex=" + firstIndex + ", lastIndex="
				+ lastIndex + ", recordCountPerPage=" + recordCountPerPage
				+ ", replacePath=" + replacePath + ", searchGubun="
				+ searchGubun + ", searchReportGubun=" + searchReportGubun
				+ ", reportUrlReal=" + reportUrlReal + ", getAtchFileId()="
				+ getAtchFileId() + ", getReportPreview()="
				+ getReportPreview() + ", getTotalRecordCount()="
				+ getTotalRecordCount() + ", getUserId()=" + getUserId()
				+ ", getMode()=" + getMode() + ", getReportSeq()="
				+ getReportSeq() + ", getReportTitle()=" + getReportTitle()
				+ ", getReportDc()=" + getReportDc() + ", getReportUrl()="
				+ getReportUrl() + ", getReportGubun()=" + getReportGubun()
				+ ", getReportUseYn()=" + getReportUseYn()
				+ ", getFrstRegistPnttm()=" + getFrstRegistPnttm()
				+ ", getFrstRegistId()=" + getFrstRegistId()
				+ ", getLastUpdtPnttm()=" + getLastUpdtPnttm()
				+ ", getLastUpusrId()=" + getLastUpusrId() + "]";
	}
    
    
    
    
    
}
