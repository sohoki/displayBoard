package com.display.backoffice.sts.sch.models;

import java.io.Serializable;

import com.display.backoffice.sts.message.models.SendMessageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class AgentGroupInfoVO extends  AgentGroupInfo  implements Serializable {

	
	
	
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
    private int totalRecordCount = 0;
    private String adminLevel = "";
    private String partId = "";
    private String agentNm = "";
    
    //콘텐츠 리스트 추가
    private String displayTitle  = "";
	private int displayNextseq = 0;
	private String displayWidth = "";
	private String displayHeight = "";
	private String displayTotalTime = "";
	private String displayLocalfile = "";
    
	
	//파일 추가 
	private String reportUrl = "";
	private String streFileNm = "";
	private String atchFileId = "";
	private String fileExtsn = "";
	private String fileSize = "";  
	private String displaySeq = "";
	
	private String conschTitle = "";
	private String conschStartday = "";
	private String conschEndday = "";
	
	
	
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
	public String getReportUrl() {
		return reportUrl;
	}
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
	public String getStreFileNm() {
		return streFileNm;
	}
	public void setStreFileNm(String streFileNm) {
		this.streFileNm = streFileNm;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getFileExtsn() {
		return fileExtsn;
	}
	public void setFileExtsn(String fileExtsn) {
		this.fileExtsn = fileExtsn;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getDisplayTitle() {
		return displayTitle;
	}
	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}
	public int getDisplayNextseq() {
		return displayNextseq;
	}
	public void setDisplayNextseq(int displayNextseq) {
		this.displayNextseq = displayNextseq;
	}
	public String getDisplayWidth() {
		return displayWidth;
	}
	public void setDisplayWidth(String displayWidth) {
		this.displayWidth = displayWidth;
	}
	public String getDisplayHeight() {
		return displayHeight;
	}
	public void setDisplayHeight(String displayHeight) {
		this.displayHeight = displayHeight;
	}
	public String getDisplayTotalTime() {
		return displayTotalTime;
	}
	public void setDisplayTotalTime(String displayTotalTime) {
		this.displayTotalTime = displayTotalTime;
	}
	public String getDisplayLocalfile() {
		return displayLocalfile;
	}
	public void setDisplayLocalfile(String displayLocalfile) {
		this.displayLocalfile = displayLocalfile;
	}
	public String getAgentNm() {
		return agentNm;
	}
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}
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
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	} 
    
	@Override
	public String toString() {
		return "AgentGroupInfoVO [searchCondition=" + searchCondition
				+ ", searchKeyword=" + searchKeyword + ", searchUseYn="
				+ searchUseYn + ", mber_Sttus=" + mber_Sttus + ", pageIndex="
				+ pageIndex + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + ", firstIndex=" + firstIndex + ", lastIndex="
				+ lastIndex + ", recordCountPerPage=" + recordCountPerPage
				+ ", totalRecordCount=" + totalRecordCount + ", adminLevel="
				+ adminLevel + ", partId=" + partId + ", agentNm=" + agentNm
				+ ", displayTitle=" + displayTitle + ", displayNextseq="
				+ displayNextseq + ", displayWidth=" + displayWidth
				+ ", displayHeight=" + displayHeight + ", displayTotalTime="
				+ displayTotalTime + ", displayLocalfile=" + displayLocalfile
				+ ", reportUrl=" + reportUrl + ", streFileNm=" + streFileNm
				+ ", atchFileId=" + atchFileId + ", fileExtsn=" + fileExtsn
				+ ", fileSize=" + fileSize + ", displaySeq=" + displaySeq
				+ ", conschTitle=" + conschTitle + ", conschStartday="
				+ conschStartday + ", conschEndday=" + conschEndday
				+ ", getConschTitle()=" + getConschTitle()
				+ ", getConschStartday()=" + getConschStartday()
				+ ", getConschEndday()=" + getConschEndday()
				+ ", getDisplaySeq()=" + getDisplaySeq() + ", getReportUrl()="
				+ getReportUrl() + ", getStreFileNm()=" + getStreFileNm()
				+ ", getAtchFileId()=" + getAtchFileId() + ", getFileExtsn()="
				+ getFileExtsn() + ", getFileSize()=" + getFileSize()
				+ ", getDisplayTitle()=" + getDisplayTitle()
				+ ", getDisplayNextseq()=" + getDisplayNextseq()
				+ ", getDisplayWidth()=" + getDisplayWidth()
				+ ", getDisplayHeight()=" + getDisplayHeight()
				+ ", getDisplayTotalTime()=" + getDisplayTotalTime()
				+ ", getDisplayLocalfile()=" + getDisplayLocalfile()
				+ ", getAgentNm()=" + getAgentNm() + ", getPartId()="
				+ getPartId() + ", getAdminLevel()=" + getAdminLevel()
				+ ", getSearchCondition()=" + getSearchCondition()
				+ ", getSearchKeyword()=" + getSearchKeyword()
				+ ", getSearchUseYn()=" + getSearchUseYn()
				+ ", getMber_Sttus()=" + getMber_Sttus() + ", getPageIndex()="
				+ getPageIndex() + ", getPageUnit()=" + getPageUnit()
				+ ", getPageSize()=" + getPageSize() + ", getFirstIndex()="
				+ getFirstIndex() + ", getLastIndex()=" + getLastIndex()
				+ ", getRecordCountPerPage()=" + getRecordCountPerPage()
				+ ", getTotalRecordCount()=" + getTotalRecordCount() + "]";
	}
    
    
    
    
}

